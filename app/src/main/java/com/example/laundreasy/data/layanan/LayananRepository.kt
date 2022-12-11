package com.example.laundreasy.data.layanan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class LayananRepository {

    var _listLayanan: MutableLiveData<MutableList<Layanan>> = MutableLiveData(mutableListOf())
    var _listIdLayanan: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    var _spinnerAdapter: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    private val db = Firebase.firestore.collection("layanan")

    init {
        updateData()
    }

    fun tambah(id: String?, nama: String, durasi: Int, harga: Double){
        if(id == null){
            db.add(Layanan(nama, durasi ,harga))
        }else{
            db.document(id).update("nama", nama)
            db.document(id).update("durasi", durasi)
            db.document(id).update("harga", harga)
        }
    }

    fun get(callback: LayananCallback){
        db.get().addOnSuccessListener {
            val response = LayananResponse()
            for (document in it) {
                response.idLayanan.add(document.id)
                response.layanan.add(document.toObject<Layanan>())
                }
            callback.onResponse(response)
            }
            .addOnFailureListener { exception ->
                Log.i("TEST", "Error getting documents: ", exception)
            }
    }

    fun edit(){

    }

    fun delete(id: String){
        db.document(id).delete().addOnSuccessListener {
            Log.w("TEST", "Success deleting document")
        }.addOnFailureListener {
            Log.w("TEST", "Error deleting document", it)
        }
    }

    fun updateData() {
        db.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TEST", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val listLayanan: MutableList<Layanan> = mutableListOf()
                val listIdLayanan: MutableList<String> = mutableListOf()
                val spinnerAdapter : MutableList<String> = mutableListOf()
                for (doc in snapshot){
                    listLayanan.add(doc.toObject<Layanan>())
                    listIdLayanan.add(doc.id)
                    spinnerAdapter.add(doc.data.get("nama").toString())
                }
                _listLayanan.value = listLayanan
                _listIdLayanan.value = listIdLayanan
                _spinnerAdapter.value = spinnerAdapter
            } else {
                Log.d("TEST", "Current data: null")
            }
        }
    }
}