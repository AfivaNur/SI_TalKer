package com.afiva.appskelurahan.data.dto.Keterangan

data class SuratKeteranganData(
    val namaPejabatKeterangan: String = "",
    val NIP: String = "",
    val jabatan: String = "",

    val nikKeterangan: String = "",
    val namaKeterangan: String = "",
    val tempatLahirKeterangan: String = "",
    val tanggalLahirKeterangan: String = "",
    val jenisKelaminKeterangan: String = "",
    val agamaKeterangan: String = "",
    val pekerjaanKeterangan: String = "",
    val alamatKeterangan: String = "",
    val tujuanSuratKeterangan: String = "",
    val selectedRTKeterangan: String = "",
    val selectedRWKeterangan: String = "",

    val expandedRTKeterangan: Boolean = false,
    val expandedRWKeterangan: Boolean = false,
    val agamaKeteranganExpanded: Boolean = false,
    val tempatLahirKeteranganExpanded: Boolean = false,

    val namaFileSuratPengantarRT: String = "",
    val namaFilePersyaratan: String = ""
)
