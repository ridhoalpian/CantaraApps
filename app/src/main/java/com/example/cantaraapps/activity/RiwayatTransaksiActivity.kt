package com.example.cantaraapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.cantaraapps.R
import com.example.cantaraapps.adapter.TabLayoutAdapter
import com.example.cantaraapps.databinding.ActivityRiwayatTransaksiBinding
import com.example.cantaraapps.ui.fragment.Profile
import com.google.android.material.tabs.TabLayout

class RiwayatTransaksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatTransaksiBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        tabLayout.addTab(tabLayout.newTab().setText("Permintaan"))
        tabLayout.addTab(tabLayout.newTab().setText("Disetujui"))
        tabLayout.addTab(tabLayout.newTab().setText("Dibuat"))
        tabLayout.addTab(tabLayout.newTab().setText("Dikirim"))
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabLayoutAdapter(this, supportFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
}