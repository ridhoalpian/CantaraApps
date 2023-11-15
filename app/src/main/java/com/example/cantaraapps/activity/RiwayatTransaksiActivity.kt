package com.example.cantaraapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cantaraapps.R
import com.example.cantaraapps.databinding.ActivityRiwayatTransaksiBinding

class RiwayatTransaksiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatTransaksiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}