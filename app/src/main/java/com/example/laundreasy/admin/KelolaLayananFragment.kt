package com.example.laundreasy.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundreasy.EditProfilActivity
import com.example.laundreasy.R
import com.example.laundreasy.data.layanan.Layanan
import com.example.laundreasy.data.layanan.LayananCallback
import com.example.laundreasy.data.layanan.LayananResponse
import com.example.laundreasy.databinding.FragmentKelolaLayananBinding
import com.example.laundreasy.ui.layanan.LayananAdapter
import com.example.laundreasy.ui.layanan.LayananViewModel

class KelolaLayananFragment : Fragment(R.layout.fragment_kelola_layanan) {

    private val layananViewModel : LayananViewModel by activityViewModels()
    private val layananAdminAdapter = LayananAdminAdapter()
    private lateinit var binding : FragmentKelolaLayananBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentKelolaLayananBinding.bind(view)
//        getData()

        layananViewModel.listLayanan.observe(viewLifecycleOwner, Observer<MutableList<Layanan>>{
            getData()
        })

        layananAdminAdapter.setOnItemClickCallback(object: LayananAdminAdapter.OnItemClickCallback{
            override fun onItemClicked(id: String, data: Layanan) {
                val intent = Intent(activity, KelolaLayananActivity::class.java)
                intent.putExtra(KelolaLayananActivity.EXTRA_ID, id)
                intent.putExtra(KelolaLayananActivity.EXTRA_NAMA, data.nama)
                intent.putExtra(KelolaLayananActivity.EXTRA_DURASI, data.durasi.toString())
                intent.putExtra(KelolaLayananActivity.EXTRA_HARGA, data.harga.toString())
                startActivity(intent)
            }

            override fun onItemClicked2(id: String, data: Layanan) {
                layananViewModel.delete(id)
            }
        })

        binding.btnTambah.setOnClickListener {
            startActivity(Intent(activity, KelolaLayananActivity::class.java))
        }
    }

    fun getData(){
        layananViewModel.getLayanan(object : LayananCallback {
            override fun onResponse(response: LayananResponse) {
                layananAdminAdapter.updateListData(response.layanan, response.idLayanan)
                binding.rvLayanan.layoutManager = LinearLayoutManager(activity)
                binding.rvLayanan.adapter = layananAdminAdapter
                binding.loading.visibility = View.GONE
                binding.rvLayanan.visibility = View.VISIBLE
            }
        })
    }


}