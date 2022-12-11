package com.example.laundreasy.admin

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.laundreasy.data.layanan.LayananCallback
import com.example.laundreasy.data.layanan.LayananResponse
import com.example.laundreasy.databinding.ActivityPesanAdminBinding
import com.example.laundreasy.databinding.ActivityPesanBinding
import com.example.laundreasy.ui.layanan.LayananViewModel
import com.example.laundreasy.ui.pesanan.PesananViewModel
import java.text.SimpleDateFormat
import java.util.*

class PesanAdminActivity : AppCompatActivity() {

    private val pesananViewModel : PesananViewModel by viewModels()
    private val layananViewModel : LayananViewModel by viewModels()
    private lateinit var binding : ActivityPesanAdminBinding
    private var spinnerPos = 0
    private val idLayanan = mutableListOf<String>()
    private val namaLayanan = mutableListOf<String>()
    val bulan = Calendar.getInstance().get(Calendar.MONTH) + 1
    val tahun = Calendar.getInstance().get(Calendar.YEAR)
    val hari = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val tanggal = SimpleDateFormat("yyyy-MM-dd").parse("$tahun-$bulan-$hari")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPesanAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layananViewModel.getLayanan(object : LayananCallback {
            override fun onResponse(response: LayananResponse) {
                for (id in response.idLayanan){
                    idLayanan.add(id)
                }
                for (layanan in response.layanan){
                    namaLayanan.add(layanan.nama)
                }
                val adapter = ArrayAdapter<String>(this@PesanAdminActivity, R.layout.simple_spinner_dropdown_item, namaLayanan)
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
            pesananViewModel.tambahAdmin(idLayanan.get(spinnerPos), namaLayanan.get(spinnerPos), binding.etNama.text.toString().trim(), binding.etNo.text.toString().trim(), binding.etBerat.text.toString().trim().toInt(), tanggal)
            finish()
            Toast.makeText(this, "Pesanan Berhasil Dibuat", Toast.LENGTH_SHORT).show()
        }
    }
}