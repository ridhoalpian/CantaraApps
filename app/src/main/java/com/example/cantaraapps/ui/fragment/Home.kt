package com.example.cantaraapps.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cantaraapps.R
import com.example.cantaraapps.activity.ChatActivity
import com.example.cantaraapps.adapter.ImageAdapterSlider
import com.example.cantaraapps.data.ImageDataBanner
import com.example.cantaraapps.data.KueModel
import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.FragmentHomeBinding
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.cantaraapps.adapter.KueAdapter
import org.json.JSONException

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterSlider: ImageAdapterSlider
    private val listBanner = ArrayList<ImageDataBanner>()
    private val listKue = ArrayList<KueModel>()
    private lateinit var dots: ArrayList<TextView>
    private lateinit var adapterKue: KueAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.whatsapp.setOnClickListener {
            val intent = Intent(requireActivity(), ChatActivity::class.java)
            startActivity(intent)
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })

        listBanner.add(ImageDataBanner(R.drawable.banner))
        listBanner.add(ImageDataBanner(R.drawable.banner1))
        listBanner.add(ImageDataBanner(R.drawable.banner2))
        adapterSlider = ImageAdapterSlider(requireContext(), listBanner)
        binding.viewPager.adapter = adapterSlider
        dots = ArrayList()
        setIndicator()

        binding.tampilanKue.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapterKue = KueAdapter(requireContext(), listKue)
        binding.tampilanKue.adapter = adapterKue
        fetchDataKueFromServer()

        return binding.root
    }

    private fun selectedDot(position: Int) {
        for (i in 0 until listBanner.size) {
            if (i == position)
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.google.android.material.R.color.m3_ref_palette_white
                    )
                )
            else
                dots[i].setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        com.google.android.material.R.color.design_default_color_secondary
                    )
                )
        }
    }

    private fun setIndicator() {
        for (i in 0 until listBanner.size) {
            dots.add(TextView(requireContext()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 10f
            binding.dots.addView(dots[i])
        }
    }


    private fun fetchDataKueFromServer() {
        val urlHomeKue = DbContract.urlHomeKue

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlHomeKue, null,
            { response ->
                try {
                    listKue.clear()

                    for (i in 0 until response.length()) {
                        val kueObject = response.getJSONObject(i)
                        val namaKue = kueObject.getString("nama_kue")
                        val kategori = kueObject.getString("kategori")

                        val kue = KueModel(namaKue, kategori)
                        listKue.add(kue)
                    }

                    adapterKue.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest)
    }
}