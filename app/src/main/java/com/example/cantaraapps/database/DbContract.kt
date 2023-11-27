package com.example.cantaraapps.database

object DbContract {
    private const val ip = "192.168.0.108"
    const val urlRegister = "http://$ip/testapi/api-register.php"
    const val urlLogin = "http://$ip/testapi/api-login.php"
    const val urlLupa = "http://$ip/testapi/api-lupapass.php"
    const val urlGanti = "http://$ip/testapi/api-gantipass.php"
    const val urlHomeKue = "http://$ip/testapi/api-home.php"
    const val urlUpdateAkun = "http://$ip/testapi/api-updateakun.php"
    const val urlKonfirPass = "http://$ip/testapi/api-konfirmasipass.php"
    const val urlPesanan = "http://$ip/testapi/api-pesanan.php"
    const val urlCariKue = "http://$ip/testapi/api-carikue.php"
}