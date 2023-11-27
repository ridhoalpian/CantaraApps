package com.example.cantaraapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.ActivitySettingAkunBinding
import org.json.JSONException
import org.json.JSONObject

class SettingAkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        val username = sharedPref?.getString("username", "")
        val nama_lengkap = sharedPref?.getString("nama", "")
        val alamat_lengkap = sharedPref?.getString("alamat_lengkap", "")
        val notelp = sharedPref?.getString("telp", "")

        if (!username.isNullOrEmpty()) {
            binding.edtNamalengkap.setText(nama_lengkap ?: "")
            binding.edtAlamat.setText(alamat_lengkap ?: "")
            binding.edtTelp.setText(notelp ?: "")
        }

        binding.btnKembaliSettingAkun.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSimpan.setOnClickListener {
            val nama = binding.edtNamalengkap.text.toString()
            val alamatlengkap = binding.edtAlamat.text.toString()
            val telp = binding.edtTelp.text.toString()

            if (nama.isNotEmpty() && alamatlengkap.isNotEmpty() && telp.isNotEmpty()) {
                updateUserDataOnServer(username, nama, alamatlengkap, telp)
            } else {
                Toast.makeText(applicationContext, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserDataToSharedPreferences(namaLengkap: String, alamat: String, telp: String) {
        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("nama", namaLengkap)
        editor.putString("alamat_lengkap", alamat)
        editor.putString("telp", telp)
        editor.apply()
    }

    private fun updateUserDataOnServer(username: String?, nama: String, alamatlengkap: String, telp: String) {
        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val url = DbContract.urlUpdateAkun

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val status = jsonObject.getString("status")
                    val message = jsonObject.getString("message")

                    if (status == "success") {
                        // Jika pembaruan berhasil, tampilkan pesan sukses
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

                        saveUserDataToSharedPreferences(nama, alamatlengkap, telp)

                        finish()
                    } else {
                        // Jika pembaruan gagal, tampilkan pesan error
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["username"] = username!!
                params["nama"] = nama
                params["alamat_lengkap"] = alamatlengkap
                params["telp"] = telp
                return params
            }
        }
        requestQueue.add(stringRequest)
    }
}