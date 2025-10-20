package com.afiva.appskelurahan.v_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.getRiwayatSurat
import com.afiva.appskelurahan.model.RiwayatSuratItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log // Import untuk Log
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.model.KeteranganData
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.model.TidakMampuData
import com.afiva.appskelurahan.model.UsahaData
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.asStateFlow

class RiwayatViewModel : ViewModel() {
    private val _riwayatList = MutableStateFlow<List<RiwayatSuratItem>>(emptyList())
    val riwayatList: StateFlow<List<RiwayatSuratItem>> = _riwayatList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val supabase = SupabaseProvider.client

    fun loadRiwayat() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val currentUser = SessionManager.currentUser
                val tables = listOf(
                    "SuratPengantarRTData",
                    "DomisiliData",
                    "KeteranganData",
                    "KematianData",
                    "TidakMampuData",
                    "UsahaData",
                    "PindahDatangData"
                )

                val result = mutableListOf<RiwayatSuratItem>()
                for (table in tables) {
                    val query = supabase.from(table).select {
                        // Apply filter for non-admin users
                        if (currentUser?.role != "admin" && currentUser?.role != "lurah") {
                            filter {
                                eq("id_user", currentUser?.id.toString())
                            }
                        }
                    }

                    val items = when (table) {
                        "SuratPengantarRTData" -> query.decodeList<SuratPengantarRTData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Surat Pengantar RT",
                                namaRiwayat = it.namaPengantar,
                                tanggalPengajuan = it.created_at,
                                status = it.status
                            )
                        }
                        "DomisiliData" -> query.decodeList<DomisiliData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Domisili",
                                namaRiwayat = it.nama,
                                tanggalPengajuan = it.createdAt.toString(),
                                status = it.status
                            )
                        }
                        "KeteranganData" -> query.decodeList<KeteranganData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Keterangan",
                                namaRiwayat = it.nama,
                                tanggalPengajuan = it.createdAt.toString(),
                                status = it.status.toString()
                            )
                        }
                        "KematianData" -> query.decodeList<KematianData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Kematian",
                                namaRiwayat = it.namaJenazah,
                                tanggalPengajuan = it.created_at,
                                status = it.status
                            )
                        }
                        "TidakMampuData" -> query.decodeList<TidakMampuData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Tidak Mampu",
                                namaRiwayat = it.nama,
                                tanggalPengajuan = it.created_at,
                                status = it.status
                            )
                        }
                        "UsahaData" -> query.decodeList<UsahaData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Usaha",
                                namaRiwayat = it.namaUsaha,
                                tanggalPengajuan = it.created_at.toString(),
                                status = it.status
                            )
                        }
                        "PindahDatangData" -> query.decodeList<PindahDatangData>().map {
                            RiwayatSuratItem(
                                id = it.id,
                                namaTabel = table,
                                jenisSurat = "Pindah Datang",
                                namaRiwayat = it.namaKepalaKeluargaAsal,
                                tanggalPengajuan = it.created_at,
                                status = it.status
                            )
                        }
                        else -> emptyList()
                    }
                    result.addAll(items)
                }
                _riwayatList.value = result.sortedByDescending { it.tanggalPengajuan }
            } catch (e: Exception) {
                Log.e("RiwayatViewModel", "Error loading riwayat: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}