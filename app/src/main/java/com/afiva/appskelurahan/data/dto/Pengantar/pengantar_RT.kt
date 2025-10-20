package com.afiva.appskelurahan.data.dto.Pengantar

data class SuratPengantarRT(
    // Form Pembuat
    var selectedRWPengantar: String = "",
    var selectedRTPengantar: String = "",
    var namaKetuaRTPengantar: String = "",
    var jabatanKetuaRT: String = "",

    // Keterangan Pembuat
    var namaPengantar: String = "",
    var nikPengantar: String = "",
    var tempatLahirPengantar: String = "",
    var tanggalLahirPengantar: String = "",
    var jenisKelaminPengantar: String = "",
    var agamaPengantar: String = "",
    var kewarganegaraan: String = "",
    var statusPerkawinanPengantar: String = "",
    var pekerjaanPengantar: String = "",
    var alamatPengantar: String = "",
    var keteranganTujuanPengantar: String = "",
    var filePathPengantar: String = "",
    var namaFilePengantar: String = ""
)
