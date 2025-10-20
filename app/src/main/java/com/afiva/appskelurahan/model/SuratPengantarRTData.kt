package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimpor!
// Hapus import androidx.room.ColumnInfo karena tidak lagi digunakan
// import androidx.room.ColumnInfo

@Serializable
data class SuratPengantarRTData(
    @SerialName("id") // Tambahkan SerialName untuk ID
    val id: Int= -1,

    @SerialName("id_user")
    val idUser: Int? = null, // Changed to nullable Int
    @SerialName("id_verifikator")
    val idVerifikator: Int? = null, // Changed to nullable Int

    @SerialName("tanggal_pengajuan")
    val tanggalPengajuan: String = "",
    @SerialName("status") // Tambahkan SerialName untuk status
    val status: String =  "",

    @SerialName("selected_rw_pengantar")
    val rwPengantar: String = "",
    @SerialName("selected_rt_pengantar")
    val rtPengantar: String = "",
    @SerialName("nama_ketua_rt_pengantar")
    val namaKetuaRtPengantar: String = "",
    @SerialName("jabatan_ketua_rt")
    val jabatanKetuaRt: String = "",
    @SerialName("nama_pengantar")
    val namaPengantar: String = "",
    @SerialName("nik_pengantar")
    val nikPengantar: String = "",
    @SerialName("tempat_lahir_pengantar")
    val tempatLahirPengantar: String = "",
    @SerialName("tanggal_lahir_pengantar")
    val tanggalLahirPengantar: String = "",
    @SerialName("jenis_kelamin_pengantar")
    val jenisKelaminPengantar: String = "",
    @SerialName("agama_pengantar")
    val agamaPengantar: String = "",
    @SerialName("kewarganegaraan") // Tambahkan SerialName untuk kewarganegaraan juga
    val kewarganegaraan: String = "Indonesia",
    @SerialName("status_perkawinan_pengantar")
    val statusPerkawinanPengantar: String = "",
    @SerialName("pekerjaan_pengantar")
    val pekerjaanPengantar: String = "",
    @SerialName("alamat_pengantar")
    val alamatPengantar: String = "",
    @SerialName("keterangan_tujuan_pengantar")
    val keteranganTujuanPengantar: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("created_at")
    val created_at: String = ""
)

@Serializable
data class SuratPengantarRTInsertData(
    val selected_rw_pengantar: String,
    val selected_rt_pengantar: String,
    val nama_ketua_rt_pengantar: String,
    val jabatan_ketua_rt: String,
    val nama_pengantar: String,
    val nik_pengantar: String,
    val tempat_lahir_pengantar: String,
    val tanggal_lahir_pengantar: String,
    val jenis_kelamin_pengantar: String,
    val agama_pengantar: String,
    val kewarganegaraan: String,
    val status_perkawinan_pengantar: String,
    val pekerjaan_pengantar: String,
    val alamat_pengantar: String,
    val keterangan_tujuan_pengantar: String,
    val nama_file_persyaratan: String,
    val id_user: Int?
)