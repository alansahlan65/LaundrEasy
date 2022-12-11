package com.example.laundreasy.ui.layanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.laundreasy.R
import com.example.laundreasy.data.layanan.Layanan

class LayananAdapter : RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    private val _listLayanan: MutableList<Layanan> = mutableListOf()

    inner class LayananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvNama : TextView = itemView.findViewById(R.id.tvNama)
        var tvDesc : TextView = itemView.findViewById(R.id.tvDesc)
        var tvHarga : TextView = itemView.findViewById(R.id.tvHarga)
        var btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layanan_list, parent, false)
        return LayananViewHolder(view)
    }

    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        holder.itemView.apply {
            holder.tvNama.text = _listLayanan[position].nama
            holder.tvDesc.text = "(Estimasi : ${_listLayanan[position].durasi} Hari)"
            holder.tvHarga.text = "Harga : Rp.${_listLayanan[position].harga}/Kg"
            holder.btnDelete.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return _listLayanan.size
    }

    fun updateListData(newLayanan : MutableList<Layanan>){
        _listLayanan.clear()
        _listLayanan.addAll(newLayanan)
    }
}