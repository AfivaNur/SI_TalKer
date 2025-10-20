package com.afiva.appskelurahan.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName // Pastikan ini diimport!
// Hapus import androidx.room.ColumnInfo karena tidak digunakan lagi
// import androidx.room.ColumnInfo
@Serializable
data class KematianData(
    @SerialName("id")
    val id: Int = -1,

//    val idUser: Int? = null, // Changed to nullable Int
//    @SerialName("id_verifikator")
    val idVerifikator: Int? = null, // Changed to nullable Int

    @SerialName("tanggal_pengajuan")
    val tanggalPengajuan: String = "",
    @SerialName("status")
    val status: String = "unverified",

    @SerialName("nama_jenazah")
    val namaJenazah: String = "",
    @SerialName("jenis_kelamin_jenazah")
    val jenisKelaminJenazah: String = "",
    @SerialName("umur_jenazah")
    val umurJenazah: String = "",
    @SerialName("pekerjaan_jenazah")
    val pekerjaanJenazah: String = "",
    @SerialName("alamat_jenazah")
    val alamatJenazah: String = "",
    @SerialName("tanggal_kematian_jenazah")
    val tanggalKematianJenazah: String = "",
    @SerialName("sebab_kematian_jenazah")
    val sebabKematianJenazah: String = "",
    @SerialName("nama_yang_menerangkan")
    val namaYangMenerangkan: String = "",
    @SerialName("selected_rt_kematian")
    val rtKematian: String = "",
    @SerialName("selected_rw_kematian")
    val rwKematian: String = "",
    @SerialName("nama_yang_melapor")
    val namaYangMelapor: String = "",
    @SerialName("hubungan_pelapor")
    val hubunganPelapor: String = "",
    @SerialName("nama_file_surat_pengantar_rt")
    val namaFileSuratPengantarRT: String = "",
    @SerialName("nama_file_persyaratan")
    val namaFilePersyaratan: String = "",
    @SerialName("created_at")
    val created_at: String = "",
    val id_user: Int?,
)

@Serializable
data class KematianInsertData(
    val nama_jenazah: String,
    val jenis_kelamin_jenazah: String,
    val umur_jenazah: String,
    val pekerjaan_jenazah: String,
    val alamat_jenazah: String,
    val tanggal_kematian_jenazah: String,
    val sebab_kematian_jenazah: String,
    val nama_yang_menerangkan: String,
    val selected_rt_kematian: String,
    val selected_rw_kematian: String,
    val nama_yang_melapor: String,
    val hubungan_pelapor: String,
    val nama_file_surat_pengantar_rt: String,
    val nama_file_persyaratan: String,
)