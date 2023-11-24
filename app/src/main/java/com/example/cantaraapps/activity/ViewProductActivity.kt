package com.example.cantaraapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cantaraapps.R
import com.example.cantaraapps.databinding.ActivityLupaPasswordBinding
import com.example.cantaraapps.databinding.ActivityMainBinding
import com.example.cantaraapps.databinding.ActivityViewProductBinding
import com.example.cantaraapps.ui.fragment.Basket

class ViewProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floatingActionButton.setOnClickListener {
            onBackPressed()
        }

        binding.buttonPesan.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        binding.buttonKeranjang.setOnClickListener {
            Toast.makeText(applicationContext, "Masuk ke Keranjang!", Toast.LENGTH_SHORT).show()
        }

        val editText = binding.editText
        val btnPlus = binding.btnPlus
        val btnMinus = binding.btnMinus

        btnPlus.setOnClickListener {
            val valueStr = editText.text.toString()

            var value = Integer.parseInt(valueStr)

            value++

            editText.setText(value.toString())
        }

        btnMinus.setOnClickListener {
            val valueStr = editText.text.toString()

            var value = Integer.parseInt(valueStr)

            if (value > 0) {
                value--
                editText.setText(value.toString())
            }
        }
    }
}