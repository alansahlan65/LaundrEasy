package com.example.laundreasy

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import androidx.fragment.app.Fragment
import com.example.laundreasy.R
import com.example.laundreasy.databinding.FragmentHomeBinding
import com.example.laundreasy.databinding.FragmentRiwayatBinding


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding : FragmentHomeBinding
    private val layananFragment = LayananFragment()
    private val riwayatFragment = RiwayatFragment()
    private val profilFragment = ProfilFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.vInformasi.setOnClickListener {
            startActivity(Intent(activity,InformasiActivity::class.java))
        }
        binding.vLayanan.setOnClickListener {
            navigateToFragment(layananFragment)
        }
        binding.vRiwayat.setOnClickListener {
            navigateToFragment(riwayatFragment)
        }
        binding.vProfil.setOnClickListener {
            navigateToFragment(profilFragment)
        }
        binding.vPesanan.setOnClickListener {
            startActivity(Intent(activity,PesanActivity::class.java))
        }
    }

    private fun navigateToFragment(fragment: Fragment){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}