package com.example.cantaraapps.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.widget.Toast
//import com.android.volley.AuthFailureError
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonDaftar.setOnClickListener {
//            registerUser()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

//    private fun registerUser() {
//        val username = binding.edtUsername.text.toString()
//        val password = binding.edtPassword.text.toString()
//        val alamat = binding.edtAlamat.text.toString()
//        val namalengkap = binding.edtNamaLengkap.text.toString()
//        val notelp = binding.edtTelp.text.toString()
//        val makanan = binding.edtLupaPass.text.toString()
//
//        if (!(username.isEmpty() || password.isEmpty() || alamat.isEmpty() || namalengkap.isEmpty() || notelp.isEmpty() || makanan.isEmpty())) {
//
//            val stringRequest: StringRequest = object : StringRequest(
//                Method.POST,
//                DbContract.urlRegister,
//                Response.Listener { response ->
//                    Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(applicationContext, LoginActivity::class.java))
//                },
//                Response.ErrorListener { error ->
//                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
//                }
//            ) {
//                @Throws(AuthFailureError::class)
//                override fun getParams(): Map<String, String> {
//                    val params: MutableMap<String, String> = HashMap()
//                    params["username"] = username
//                    params["password"] = password
//                    params["alamat"] = alamat
//                    params["nama_lengkap"] = namalengkap
//                    params["no_telp"] = notelp
//                    params["makanan"] = makanan
//                    return params
//                }
//            }
//
//            val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
//            requestQueue.add(stringRequest)
//        } else {
//            Toast.makeText(applicationContext, "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show()
//        }
//    }
}