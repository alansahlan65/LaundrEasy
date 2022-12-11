package com.example.laundreasy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.laundreasy.R
import com.example.laundreasy.data.layanan.LayananCallback
import com.example.laundreasy.data.layanan.LayananResponse
import com.example.laundreasy.databinding.FragmentLayananBinding
import com.example.laundreasy.ui.layanan.LayananAdapter
import com.example.laundreasy.ui.layanan.LayananViewModel

class LayananFragment : Fragment(R.layout.fragment_layanan) {

    private val layananViewModel : LayananViewModel by activityViewModels()
    private lateinit var binding : FragmentLayananBinding
    private val layananAdapter = LayananAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLayananBinding.bind(view)

//        layananViewModel.listLayanan.observe(viewLifecycleOwner){
//            layananAdapter.updateListData(it)
//            binding.rvLayanan.layoutManager = LinearLayoutManager(activity)
//            binding.rvLayanan.adapter = layananAdapter
//            binding.loading.visibility = View.GONE
//            binding.rvLayanan.visibility = View.VISIBLE
//        }

        layananViewModel.getLayanan(object : LayananCallback {
            override fun onResponse(response: LayananResponse) {
                layananAdapter.updateListData(response.layanan)
                binding.rvLayanan.layoutManager = LinearLayoutManager(activity)
                binding.rvLayanan.adapter = layananAdapter
                binding.loading.visibility = View.GONE
                binding.rvLayanan.visibility = View.VISIBLE
            }
        })
    }
}