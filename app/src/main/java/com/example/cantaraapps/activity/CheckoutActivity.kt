package com.example.cantaraapps.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.cantaraapps.database.DbContract
import com.example.cantaraapps.databinding.ActivityCheckoutBinding
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.concurrent.TimeUnit

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private var selectedImageUri: Uri? = null

    private val pickImage: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                selectedImageUri = data?.data

                binding.edtNamaFile.setText(getFileName(selectedImageUri))
            }
        }


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
        val totalHarga = intent.getStringExtra("total_harga")?.toInt() ?: 0

        val textToCopy = "6282389422820"
        val ongkirr = 10000

        val totalsemua = totalHarga + ongkirr

        binding.ongkoskrm.text = "Ongkos Kirim Rp.${ongkirr}"
        binding.totalhrg.text = "Rp. $totalsemua"

        binding.floatingActionButton.setOnClickListener{
            onBackPressed()
        }

        binding.btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImage.launch(intent)
        }


        binding.copyBCA.setOnClickListener {
            copyToClipboard(textToCopy)
        }

        binding.tglditerima.setOnClickListener {
            showDatePickerDialog()
        }

        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        binding.tglpemesanan.text = currentDate

        binding.buttonPesan.setOnClickListener {
            insertDataToDatabase(iduser, totalHarga, idKue, jumlah)
        }

        binding.namaKue.text = namaKue
        binding.hrgkue.text = "Rp. $hargaKue"
        binding.bnykkue.text = "$jumlah $satuan"


        if (gambar != null && gambar!!.isNotEmpty()) {
            val decodedImage = Base64.decode(gambar, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.size)
            binding.gambarKue.setImageBitmap(bitmap)
        }

        binding.nomerakun.text = textToCopy
    }

    private fun getFileName(uri: Uri?): String {
        var result: String? = null
        if (uri?.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (displayNameIndex >= 0) {
                        result = it.getString(displayNameIndex)
                    } else {
                        result = "unknown"
                    }
                }
            }
        }
        if (result == null) {
            result = uri?.path
            val cut = result?.lastIndexOf('/')
            if (cut != null && cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "unknown"
    }

    private fun copyToClipboard(text: CharSequence) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun showDatePickerDialog() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)

                val selisihMillis = selectedCalendar.timeInMillis - currentDate.timeInMillis
                val selisihHari = TimeUnit.MILLISECONDS.toDays(selisihMillis)

                if (selisihHari >= 3) {
                    val newDate = selectedCalendar.time
                    val tanggalDiterima = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(newDate)
                    binding.tglditerima.text = tanggalDiterima
                } else {
                    Toast.makeText(this, "Jarak tanggal penerimaan terlalu dekat. Silahkan pilih tanggal lain", Toast.LENGTH_SHORT).show()
                }
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

    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun convertImageToByteArray(uri: Uri): ByteArray {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        return inputStream?.readBytes() ?: byteArrayOf()
    }

    private fun insertDataToDatabase(
        iduser: String?,
        totalHarga: Int?,
        idKue: String?,
        jumlah: String?
    ) {
        val idPesanan = generateIdPesanan()
        val idDetailPesanan = generateIdDetailPesanan()

        val pesanText = binding.edtPesan.text.toString()

        if (pesanText.isBlank()) {
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
                params["tgl_pesan"] = binding.tglpemesanan.text.toString()
                params["tgl_terima"] = binding.tglditerima.text.toString()
                params["pesan"] = pesanText
                params["id_detailpesanan"] = idDetailPesanan
                params["id_kue"] = idKue.toString()
                params["jumlah_pesan"] = jumlah.toString()
                params["harga"] = totalHarga.toString()

                // Convert the image to Base64 and add it to params
                selectedImageUri?.let {
                    val imageByteArray = convertImageToByteArray(it)
                    params["bukti"] = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                }
                return params
            }
        }

        val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}