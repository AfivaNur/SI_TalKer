package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable

@Serializable
data class RiwayatSuratItem(
    val id: Int,
    val jenisSurat: String,
    val namaTabel: String,
    val namaRiwayat: String,
    val tanggalPengajuan: String,
    val status: String
)