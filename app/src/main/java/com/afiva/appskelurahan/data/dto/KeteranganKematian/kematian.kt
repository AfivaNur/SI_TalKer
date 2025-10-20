package com.afiva.appskelurahan.data.dto.KeteranganKematian

data class SuratKeteranganKematian(
    var namaJenazah: String = "",
    var jenisKelaminJenazah: String = "",
    var umurJenazah: String = "",
    var pekerjaanJenazah: String = "",
    var alamatJenazah: String = "",
    var tanggalKematianJenazah: String = "",
    var sebabKematianJenazah: String = "",
    var sebabKematianExpanded: Boolean = false,
    var namayangMenerangkan: String = "",
    var yangMenerangkanExpanded: String = "",

    var selectedRTKematian: String = "",
    var selectedRWKematian: String = "",
    var expandedRWKematian: Boolean = false,
    var expandedRTKematian: Boolean = false,

    var namaYangMelapor: String = "",
    var hubunganPelapor: String = "",
    var hubunganPelaporExpanded: Boolean = false
)