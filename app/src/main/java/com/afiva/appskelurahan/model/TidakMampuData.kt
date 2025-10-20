package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimpor!
// Hapus import androidx.room.ColumnInfo karena tidak lagi digunakan
// import androidx.room.ColumnInfo

@Serializable
data class TidakMampuData(
    @SerialName("id") // Tambahkan SerialName untuk ID
    val id: Int = -1,

//    @SerialName("id_user")
//    val idUser: Int? = null, // Changed to nullable Int
    @SerialName("id_verifikator")
    val idVerifikator: Int? = null,

    @SerialName("tanggal_pengajuan")
    val tanggalPengajuan: String = "",
    @SerialName("status") // Tambahkan SerialName untuk status
    val status: String = "unverified",

    @SerialName("nama") // Tambahkan SerialName untuk nama
    val nama: String = "",
    @SerialName("nik_tidak_mampu")
    val nik: String = "",
    @SerialName("tanggal_lahir")
    val tanggalLahir: String = "",
    @SerialName("tempat_lahir")
    val tempatLahir: String = "",
    @SerialName("jenis_kelamin")
    val jenisKelamin: String = "",
    @SerialName("agama") // Tambahkan SerialName untuk agama
    val agama: String = "",
    @SerialName("kewarganegaraan") // Tambahkan SerialName untuk kewarganegaraan
    val kewarganegaraan: String = "Indonesia",
    @SerialName("pekerjaan") // Tambahkan SerialName untuk pekerjaan
    val pekerjaan: String = "",
    @SerialName("alamat") // Tambahkan SerialName untuk alamat
    val alamat: String = "",
    @SerialName("tujuan_surat")
    val tujuanSurat: String = "",
    @SerialName("selected_rt")
    val rt: String = "",
    @SerialName("selected_rw")
    val rw: String = "",
    @SerialName("kategori_keterangan")
    val kategoriKeterangan: String = "",
    @SerialName("nama_file_surat_pengantar_rt")
    val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("created_at")
    val created_at: String = "",
    val id_user: Int?,
)

@Serializable
data class TidakMampuInsertData(
    val nama: String,
    val nik_tidak_mampu: String,
    val tanggal_lahir: String,
    val tempat_lahir: String,
    val jenis_kelamin: String,
    val agama: String,
    val kewarganegaraan: String,
    val pekerjaan: String,
    val alamat: String,
    val tujuan_surat: String,
    val selected_rt: String,
    val selected_rw: String,
    val kategori_keterangan: String,
    val nama_file_surat_pengantar_rt: String,
    val nama_file_persyaratan: String,
    val id_user: Int?,
)
