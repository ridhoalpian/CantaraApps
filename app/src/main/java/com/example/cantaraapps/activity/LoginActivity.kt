package com.example.cantaraapps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import android.widget.Toast
//import com.android.volley.Request
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMasuk.setOnClickListener {
//            loginUser()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.daftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.txtLupapass.setOnClickListener {
            startActivity(Intent(this, LupaPasswordActivity::class.java))
        }
    }

//    private fun loginUser() {
//        val username = binding.edtUser.text.toString()
//        val password = binding.edtPass.text.toString()
//
//        if (!(username.isEmpty() || password.isEmpty())) {
//
//            val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
//
//            val stringRequest = StringRequest(
//                Request.Method.GET,
//                "${DbContract.urlLogin}?username=$username&password=$password",
//                Response.Listener { response ->
//                    if (response == "Selamat Datang") {
//                        Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(applicationContext, MainActivity::class.java))
//                    } else {
//                        Toast.makeText(applicationContext, "Login Gagal", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                Response.ErrorListener { error ->
//                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
//                }
//            )
//            requestQueue.add(stringRequest)
//        } else {
//            Toast.makeText(applicationContext, "Password Atau Username Salah", Toast.LENGTH_SHORT).show()
//        }
//    }
}
