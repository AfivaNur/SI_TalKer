package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimpor!
// Hapus import androidx.room.ColumnInfo karena tidak digunakan lagi
// import androidx.room.ColumnInfo
// KeteranganData (diperbaiki untuk menangani null)
@Serializable
data class KeteranganData(
    @SerialName("id") val id: Int = -1,
//    @SerialName("id_user") val idUser: Int? = null, // Nullable untuk menangani "id_user":null
    @SerialName("id_verifikator") val idVerifikator: Int? = null, // Nullable untuk menangani "id_verifikator":null
    @SerialName("tanggal_pengajuan") val tanggalPengajuan: String? = null,
    @SerialName("status") val status: String = "unverified",
    @SerialName("nama_pejabat") val namaPejabat: String = "",
    @SerialName("jabatan") val jabatan: String = "",
    @SerialName("nik") val nik: String = "",
    @SerialName("nip") val nip: String = "",
    @SerialName("nama") val nama: String = "",
    @SerialName("tempat_lahir") val tempatLahir: String = "",
    @SerialName("tanggal_lahir") val tanggalLahir: String = "",
    @SerialName("jenis_kelamin") val jenisKelamin: String = "",
    @SerialName("warga_negara") val wargaNegara: String = "",
    @SerialName("agama") val agama: String = "",
    @SerialName("pekerjaan") val pekerjaan: String = "",
    @SerialName("alamat") val alamat: String = "",
    @SerialName("tujuan_surat") val tujuanSurat: String = "",
    @SerialName("rt") val rt: String = "",
    @SerialName("rw") val rw: String = "",
    @SerialName("nama_file_surat_pengantar_rt") val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan") val namaFilePersyaratan: String = "",
    @SerialName("created_at") val createdAt: String? = null, // Gunakan camelCase untuk konsistensi
    val id_user: Int?
)

@Serializable
data class KeteranganInsertData(
    val nama_pejabat: String?,
    val nip: String?,
    val jabatan: String?,
    val nik: String?,
    val nama: String?,
    val tempat_lahir: String?,
    val tanggal_lahir: String?,
    val jenis_kelamin: String?,
    val warga_negara: String?,
    val agama: String?,
    val pekerjaan: String?,
    val alamat: String?,
    val tujuan_surat: String?,
    val rt: String?,
    val rw: String?,
    val nama_file_surat_pengantar_rt: String?,
    val nama_file_persyaratan: String?,
    val id_user: Int?
)
