package com.example.cantaraapps.ui.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.cantaraapps.R
import com.example.cantaraapps.activity.ChatActivity
import com.example.cantaraapps.activity.ViewProductActivity
import com.example.cantaraapps.adapter.ImageAdapterSlider
import com.example.cantaraapps.data.ImageDataBanner
import com.example.cantaraapps.databinding.FragmentHomeBinding


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: ImageAdapterSlider
    private val list = ArrayList<ImageDataBanner>()
    private lateinit var dots: ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.materialCardView.setOnClickListener {
            val intent = Intent(requireActivity(), ViewProductActivity::class.java)
            startActivity(intent)
        }

        binding.whatsapp.setOnClickListener {
            val intent = Intent(requireActivity(), ChatActivity::class.java)
            startActivity(intent)
        }

        list.add(ImageDataBanner(R.drawable.banner))
        list.add(ImageDataBanner(R.drawable.banner1))
        list.add(ImageDataBanner(R.drawable.banner2))

        adapter = ImageAdapterSlider(requireContext(), list)
        binding.viewPager.adapter = adapter
        dots = ArrayList()
        setIndicator()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })

        return binding.root
    }

    private fun selectedDot(position: Int) {
        for(i in 0 until list.size){
            if (i == position)
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.m3_ref_palette_white))
            else
                dots[i].setTextColor(ContextCompat.getColor(requireContext(), com.google.android.material.R.color.design_default_color_secondary))
        }
    }

    private fun setIndicator() {
        for(i in 0 until list.size){
            dots.add(TextView(requireContext()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                dots[i].text = Html.fromHtml("&#9679")
            }
            dots[i].textSize = 10f
            binding.dots.addView(dots[i])
        }
    }
}
