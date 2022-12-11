package com.example.laundreasy.ui.layanan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.laundreasy.data.layanan.Layanan
import com.example.laundreasy.data.layanan.LayananCallback
import com.example.laundreasy.data.layanan.LayananRepository
import com.example.laundreasy.data.user.UserRepository

class LayananViewModel : ViewModel() {

    private var _listLayanan: MutableLiveData<MutableList<Layanan>> = MutableLiveData(mutableListOf())
    val listLayanan: LiveData<MutableList<Layanan>>
        get() = _listLayanan

    private var _spinnerAdapter: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val spinnerAdapter: LiveData<MutableList<String>>
        get() = _spinnerAdapter

    private val repository = LayananRepository()

    init {
        _listLayanan = repository._listLayanan
        _spinnerAdapter = repository._spinnerAdapter
    }

    fun tambah(id: String?, nama: String, durasi: Int, harga: Double){
        repository.tambah(id, nama, durasi, harga)
    }

    fun getLayanan(callback: LayananCallback){
        repository.get(callback)
    }

    fun edit(){

    }

    fun delete(id: String){
        repository.delete(id)
    }
}