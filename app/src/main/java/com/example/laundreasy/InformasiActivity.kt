package com.example.laundreasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laundreasy.databinding.ActivityInformasiBinding
import com.example.laundreasy.databinding.ActivityPesanBinding

class InformasiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInformasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView6.text = "LaundrEasy merupakan sebuah aplikasi pemesanan dan pencatatan jasa laundry berbasis android. Aplikasi ini dapat mempermudah customer dalam memesan jasa laundry serta pencatatan bagi pemilik laundry. Aplikasi LaundrEasy dibangun agar dapat mempermudah pemesanan jasa, mulai dari opsi antar-jemput pesanan, pemesanan secara online, hingga tracking status pesanan yang diharapkan akan bekerja lebih efisien dan lebih hemat. Selain itu pengguna sebagai admin dapat melakukan pencatatan secara digital yang langsung tersimpan ke dalam database sehingga tidak perlu lagi melakukan pencatatan fisik di atas kertas. Dengan menggunakan aplikasi ini, pemesanan jasa maupun pengelolaan laundry diharapkan dapat dilakukan dengan lebih mudah."
    }
}