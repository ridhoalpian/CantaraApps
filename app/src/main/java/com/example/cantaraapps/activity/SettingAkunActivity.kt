package com.example.cantaraapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cantaraapps.R
import com.example.cantaraapps.databinding.ActivitySettingAkunBinding

class SettingAkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingAkunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}