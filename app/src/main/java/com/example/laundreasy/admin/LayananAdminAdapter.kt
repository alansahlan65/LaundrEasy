package com.example.laundreasy.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laundreasy.R
import com.example.laundreasy.data.layanan.Layanan
import com.example.laundreasy.data.layanan.LayananRepository

class LayananAdminAdapter : RecyclerView.Adapter<LayananAdminAdapter.LayananViewHolder>() {

    private val _listLayanan: MutableList<Layanan> = mutableListOf()
    private val _listIdLayanan: MutableList<String> = mutableListOf()
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(id: String, data: Layanan)
        fun onItemClicked2(id: String, data: Layanan)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
            holder.tvDesc.text = "Durasi : ${_listLayanan[position].durasi} Hari"
            holder.tvHarga.text = " Harga : Rp.${_listLayanan[position].harga}/Kg"
            holder.itemView.setOnClickListener {onItemClickCallback.onItemClicked(_listIdLayanan[holder.adapterPosition], _listLayanan[holder.adapterPosition])}
            holder.btnDelete.setOnClickListener { onItemClickCallback.onItemClicked2(_listIdLayanan[holder.adapterPosition], _listLayanan[holder.adapterPosition])}
        }
    }

    override fun getItemCount(): Int {
        return _listLayanan.size
    }

    fun updateListData(newLayanan : MutableList<Layanan>, newIdLayanan : MutableList<String>){
        _listLayanan.clear()
        _listLayanan.addAll(newLayanan)

        _listIdLayanan.clear()
        _listIdLayanan.addAll(newIdLayanan)
    }
}