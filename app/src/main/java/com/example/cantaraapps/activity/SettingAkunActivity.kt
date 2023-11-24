package com.example.cantaraapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
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

        if (!username.isNullOrEmpty()) {
            // Ambil dan tampilkan data pengguna dari server
            fetchUserDataFromServer(username)
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

    private fun fetchUserDataFromServer(username: String?) {
        // Buat request untuk mengambil data pengguna dari server
        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val url = "${DbContract.urlTampilData}?username=$username" // Gantilah dengan URL yang sesuai

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Proses respons JSON dan tampilkan data pada TextView atau bidang lainnya
                try {
                    val jsonObject = JSONObject(response)
                    val namaLengkap = jsonObject.getString("nama")
                    val alamat = jsonObject.getString("alamat_lengkap")
                    val telp = jsonObject.getString("telp")

                    // Tampilkan data pada TextView atau bidang lainnya
                    binding.edtNamalengkap.setText(namaLengkap)
                    binding.edtAlamat.setText(alamat)
                    binding.edtTelp.setText(telp)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )

        requestQueue.add(stringRequest)
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