package com.example.laundreasy.data.pesanan

import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import com.example.laundreasy.data.layanan.Layanan
import com.example.laundreasy.data.layanan.LayananResponse
import com.example.laundreasy.data.user.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.timerTask

class PesananRepository {

    var _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    var _listPesanan: MutableLiveData<MutableList<Pesanan>> = MutableLiveData(mutableListOf())
    var _listAllPesanan: MutableLiveData<MutableList<Pesanan>> = MutableLiveData(mutableListOf())
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    init {
        if (auth.currentUser != null) {
            _user.value = auth.currentUser
            updateData()
        }
        adminUpdateData()
    }

    fun tambah(idLayanan: String, namaLayanan: String, berat: Int, tanggal: Date) {
        db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
            val userData = it.toObject<User>()
            db.collection("layanan").document(idLayanan).get().addOnSuccessListener {
                val totalHarga = it.data!!.get("harga").toString().toDouble() * berat
                db.collection("pesanan")
                    .add(Pesanan(auth.currentUser!!.uid, auth.currentUser!!.email!!, userData!!.nama, userData.no, idLayanan, namaLayanan, berat, totalHarga, tanggal))
            }
        }
    }

    fun tambahAdmin(idLayanan: String, namaLayanan: String, namaCostumer: String, noCostumer:String, berat: Int, tanggal: Date) {
        db.collection("layanan").document(idLayanan).get().addOnSuccessListener {
            val totalHarga = it.data!!.get("harga").toString().toDouble() * berat
            db.collection("pesanan")
                .add(Pesanan("", "", namaCostumer, noCostumer, idLayanan, namaLayanan, berat, totalHarga, tanggal))
        }
    }

    fun get(callback: PesananCallback) {
        db.collection("pesanan").whereEqualTo("idCustomer", auth.currentUser!!.uid).get().addOnSuccessListener {
            val response = PesananResponse()
            val listPesanan : MutableList<Pesanan> = mutableListOf()
            for (document in it){
                response.pesanan.add(document.toObject<Pesanan>())
                response.idPesanan.add(document.id)
                listPesanan.add(document.toObject<Pesanan>())
            }
            callback.onResponse(response)
//            _listPesanan.value = listPesanan
        }
    }

    fun getAll(callback: PesananCallback){
        db.collection("pesanan").get().addOnSuccessListener {
            val response = PesananResponse()
            for (document in it){
                response.pesanan.add(document.toObject<Pesanan>())
                response.idPesanan.add(document.id)
            }
            callback.onResponse(response)
        }
    }



    fun edit(idPesanan: String, status: String) {
        db.collection("pesanan").document(idPesanan).update("status", status)
    }

    fun delete() {

    }

    fun updateData() {
        db.collection("pesanan").whereEqualTo("idCustomer", auth.currentUser!!.uid).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TEST", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val listPesanan: MutableList<Pesanan> = mutableListOf()
                for (doc in snapshot){
                    listPesanan.add(doc.toObject<Pesanan>())
                }
                _listPesanan.value = listPesanan
            } else {
                Log.d("TEST", "Current data: null")
            }
        }
    }

    fun adminUpdateData(){
        db.collection("pesanan").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("TEST", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val listAllPesanan: MutableList<Pesanan> = mutableListOf()
                for (doc in snapshot){
                    listAllPesanan.add(doc.toObject<Pesanan>())
                }
                _listAllPesanan.value = listAllPesanan
            } else {
                Log.d("TEST", "Current data: null")
            }
        }
    }

}