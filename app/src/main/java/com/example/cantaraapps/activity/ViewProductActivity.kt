package com.example.cantaraapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cantaraapps.R
import com.example.cantaraapps.databinding.ActivityLupaPasswordBinding
import com.example.cantaraapps.databinding.ActivityMainBinding
import com.example.cantaraapps.databinding.ActivityViewProductBinding

class ViewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}