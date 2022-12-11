package com.example.laundreasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.laundreasy.databinding.ActivityMainBinding
import com.example.laundreasy.ui.layanan.LayananViewModel
import com.example.laundreasy.ui.riwayat.RiwayatViewModel
import com.example.laundreasy.ui.user.UserViewModel
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity(){

    private val userViewModel : UserViewModel by viewModels()
    private val layananViewModel : LayananViewModel by viewModels()
    private val riwayatViewModel : RiwayatViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val layananFragment = LayananFragment()
    private val riwayatFragment = RiwayatFragment()
    private val profilFragment = ProfilFragment()
    private var title : String = "Home"
    val TAG = "TEST"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navBottom.menu.get(2).isEnabled = false
        setActionBarTitle(title)

        userViewModel.user.observe(this, Observer<FirebaseUser>{ user ->
            if(user == null){
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        })

        binding.navBottom.setOnItemSelectedListener {
            setMode(it.itemId)
            true
        }

        binding.btnPesan.setOnClickListener{
            startActivity(Intent(this@MainActivity, PesanActivity::class.java))
        }
    }

    fun setMode(selectedMode: Int) {
        when (selectedMode){
            R.id.home -> {
                navigateToFragment(homeFragment)
                title ="Home"
            }
            R.id.layanan -> {
                navigateToFragment(layananFragment)
                title ="Layanan"
            }
            R.id.riwayat -> {
                navigateToFragment(riwayatFragment)
                title ="Riwayat"
            }
            R.id.profil -> {
                navigateToFragment(profilFragment)
                title ="Profil"
            }
        }
        setActionBarTitle(title)
    }

    private fun navigateToFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

}