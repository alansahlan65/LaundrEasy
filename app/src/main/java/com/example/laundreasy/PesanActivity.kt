package com.example.laundreasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.laundreasy.data.layanan.LayananCallback
import com.example.laundreasy.data.layanan.LayananResponse
import com.example.laundreasy.databinding.ActivityPesanBinding
import com.example.laundreasy.ui.layanan.LayananViewModel
import com.example.laundreasy.ui.pesanan.PesananViewModel
import java.text.SimpleDateFormat
import java.util.*

class PesanActivity : AppCompatActivity() {

    private val pesananViewModel : PesananViewModel by viewModels()
    private val layananViewModel : LayananViewModel by viewModels()
    private lateinit var binding : ActivityPesanBinding
    private var spinnerPos = 0
    private val idLayanan = mutableListOf<String>()
    private val namaLayanan = mutableListOf<String>()
    val bulan = Calendar.getInstance().get(Calendar.MONTH) + 1
    val tahun = Calendar.getInstance().get(Calendar.YEAR)
    val hari = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val tanggal = SimpleDateFormat("yyyy-MM-dd").parse("$tahun-$bulan-$hari")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layananViewModel.getLayanan(object : LayananCallback {
            override fun onResponse(response: LayananResponse) {
                for (id in response.idLayanan){
                    idLayanan.add(id)
                }
                for (layanan in response.layanan){
                    namaLayanan.add(layanan.nama)
                }
                val adapter = ArrayAdapter<String>(this@PesanActivity, android.R.layout.simple_spinner_dropdown_item, namaLayanan)
                binding.spinner.adapter = adapter
            }
        })

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerPos = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnPesan.setOnClickListener {
            pesananViewModel.tambah(idLayanan.get(spinnerPos), namaLayanan.get(spinnerPos), binding.etBerat.text.toString().trim().toInt(), tanggal)
            finish()
            Toast.makeText(this, "Pesanan Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
        }
    }
}