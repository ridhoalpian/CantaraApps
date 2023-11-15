package com.example.cantaraapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cantaraapps.R
import com.example.cantaraapps.databinding.ActivityRiwayatTransaksiBinding
import com.example.cantaraapps.databinding.ActivityUbahPassBinding

class UbahPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUbahPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahPassBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}