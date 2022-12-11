package com.example.laundreasy.data.pesanan

import com.example.laundreasy.data.layanan.LayananResponse

interface PesananCallback {
    fun onResponse(response: PesananResponse)
}