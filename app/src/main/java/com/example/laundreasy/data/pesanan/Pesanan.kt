package com.example.laundreasy.data.pesanan

import java.util.*

data class Pesanan(
    val idCustomer : String = "",
    val emailCostumer: String ="",
    val namaCostumer: String ="",
    val noCostumer: String ="",
    val idLayanan: String = "",
    val namaLayanan: String = "",
    val berat: Int = 0,
    val totalHarga: Double = 0.0,
    val tanggal: Date = Date(),
    val status: String = "Menunggu Konfirmasi")