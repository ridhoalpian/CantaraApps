package com.example.cantaraapps.activity

import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.ActivityCheckoutBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_data", MODE_PRIVATE)
        val iduser = sharedPref.getString("id_user", "")
        val nama = sharedPref.getString("nama", "")
        val telp = sharedPref.getString("telp", "")
        val alamat = sharedPref.getString("alamat_lengkap", "")
        val kecamatan = sharedPref.getString("kecamatan", "")

        val namaTelp = "$nama - ($telp)"
        binding.namadantelp.text = namaTelp

        val fullAddress = "Kecamatan $kecamatan, $alamat"
        binding.alamatuser.text = fullAddress

        val idKue = intent.getStringExtra("id_kue")
        val namaKue = intent.getStringExtra("nama_kue")
        val hargaKue = intent.getStringExtra("harga_kue")
        val satuan = intent.getStringExtra("satuan")
        val gambar = intent.getStringExtra("gambar")
        val jumlah = intent.getStringExtra("jumlah")
        val totalHarga = intent.getStringExtra("total_harga")

        val textToCopy = "6282389422820"

        binding.floatingActionButton.setOnClickListener{
            onBackPressed()
        }

        binding.btnRiwayatTran.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.copyBCA.setOnClickListener {
            copyToClipboard(textToCopy)
        }

        binding.pilihtanggal.setOnClickListener {
            showDatePickerDialog()
        }

        binding.buttonPesan.setOnClickListener {
            insertDataToDatabase(iduser, totalHarga, idKue, jumlah)
        }

        binding.namaKue.text = namaKue
        binding.hrgkue.text = "Rp. $hargaKue"
        binding.bnykkue.text = "$jumlah $satuan"
        binding.totalhrg.text = "Rp. $totalHarga"


        if (gambar != null && gambar!!.isNotEmpty()) {
            val decodedImage = Base64.decode(gambar, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.size)
            binding.gambarKue.setImageBitmap(bitmap)
        }

        binding.nomerakun.text = textToCopy
    }

    private fun copyToClipboard(text: CharSequence) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val tanggalPesan = "$year-${monthOfYear + 1}-$dayOfMonth"

                // Parse the selected date
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)

                // Add 3 days to the selected date
                selectedCalendar.add(Calendar.DAY_OF_MONTH, 3)

                // Format the new date
                val newDate = selectedCalendar.time
                val tanggalDiterima = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate)

                // Set the text for tglditentukan and diterima
                binding.tglditentukan.text = tanggalPesan
                binding.tglditerima.text = tanggalDiterima
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun generateIdPesanan(): String {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val randomCode = generateRandomCode()
        return "P$currentDate$randomCode"
    }

    fun generateIdDetailPesanan(): String {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val randomCode = generateRandomCode()
        return "DP$currentDate$randomCode"
    }

    fun generateRandomCode(): String {
        val random = Random()
        val randomCode = StringBuilder()
        repeat(3) {
            randomCode.append(random.nextInt(10))
        }
        return randomCode.toString()
    }

    private fun insertDataToDatabase(
        iduser: String?,
        totalHarga: String?,
        idKue: String?,
        jumlah: String?
    ) {
        val idPesanan = generateIdPesanan()
        val idDetailPesanan = generateIdDetailPesanan()

        val pesanText = binding.edtPesan.text.toString()

        if (pesanText.isBlank()) {
            // Show a toast message indicating that all data must be filled
            Toast.makeText(applicationContext, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST,
            DbContract.urlPesanan,
            Response.Listener { response ->
                Log.d("InsertData", "Response: $response")
                Toast.makeText(applicationContext, response.toString(), Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener { error ->
                Log.e("InsertData", "Error: $error")
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["id_pesanan"] = idPesanan
                params["id_user"] = iduser.toString()
                params["total_harga"] = totalHarga.toString()
                params["tgl_pesan"] = binding.tglditentukan.text.toString()
                params["tgl_terima"] = binding.tglditerima.text.toString()
                params["pesan"] = pesanText
                params["id_detailpesanan"] = idDetailPesanan
                params["id_kue"] = idKue.toString()
                params["jumlah_pesan"] = jumlah.toString()
                params["harga"] = totalHarga.toString()
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}