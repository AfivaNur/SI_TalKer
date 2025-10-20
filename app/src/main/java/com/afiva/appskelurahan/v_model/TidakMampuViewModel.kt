package com.afiva.appskelurahan.v_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.TidakMampuData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TidakMampuViewModel : ViewModel() {

    private val _dataTidakMampu = MutableStateFlow<TidakMampuData?>(null)
    val dataTidakMampu: StateFlow<TidakMampuData?> = _dataTidakMampu.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    // Tambahkan state untuk unverified pengajuan
    private val _hasUnverifiedPengajuan = mutableStateOf(false)
    val hasUnverifiedPengajuan: State<Boolean> = _hasUnverifiedPengajuan

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanDataTidakMampu(data: TidakMampuData) {
        _dataTidakMampu.value = data
    }

    fun resetData() {
        val idUser = SessionManager.currentUser?.id ?: 0
        _dataTidakMampu.value = TidakMampuData(id_user = idUser)
    }

    // PERBAIKAN 1: Pisahkan fungsi untuk mengecek active dan unverified
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    // Cek pengajuan yang sedang diproses (bukan unverified)
                    val response = supabase.postgrest["TidakMampuData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                // Cek status selain unverified (misal: processing, approved, dll)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<TidakMampuData>()
                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("TidakMampuViewModel", "Pengajuan aktif untuk id_user $idUser: ${response.size} ditemukan")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("TidakMampuViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("TidakMampuViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}", e)
                _hasActivePengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // PERBAIKAN 2: Tambahkan fungsi terpisah untuk mengecek unverified
    fun checkUnverifiedPengajuan(idUser: String?) {
        viewModelScope.launch {
            try {
                if (idUser != null) {
                    val response = supabase.postgrest["TidakMampuData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<TidakMampuData>()
                    _hasUnverifiedPengajuan.value = response.isNotEmpty()
                    Log.d("TidakMampuViewModel", "Pengajuan unverified untuk id_user $idUser: ${response.size} ditemukan")
                } else {
                    _hasUnverifiedPengajuan.value = false
                    Log.e("TidakMampuViewModel", "ID pengguna tidak tersedia untuk cek unverified")
                }
            } catch (e: Exception) {
                Log.e("TidakMampuViewModel", "Gagal memeriksa pengajuan unverified: ${e.message}", e)
                _hasUnverifiedPengajuan.value = false
            }
        }
    }

    // PERBAIKAN 3: Ganti nama tabel yang benar dan perbaiki komentar
    fun submitPengajuan(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = _dataTidakMampu.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    // PERBAIKAN 4: Gunakan nama tabel yang benar
                    supabase.postgrest["TidakMampuData"].insert(insertData)
                    Log.d("TidakMampuViewModel", "Berhasil mengajukan data tidak mampu")
                    onSuccess()
                } else {
                    onError("Data tidak mampu tidak lengkap")
                }
            } catch (e: Exception) {
                // PERBAIKAN 5: Perbaiki nama log
                Log.e("TidakMampuViewModel", "Gagal mengajukan: ${e.message}", e)
                onError("Gagal mengajukan: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // OPTIONAL: Tambahkan fungsi untuk mengecek semua kondisi sekaligus
    fun checkAllPengajuanStatus(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    // Cek unverified
                    val unverifiedResponse = supabase.postgrest["TidakMampuData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<TidakMampuData>()
                    _hasUnverifiedPengajuan.value = unverifiedResponse.isNotEmpty()

                    // Cek active (processing, approved, dll)
                    val activeResponse = supabase.postgrest["TidakMampuData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<TidakMampuData>()
                    _hasActivePengajuan.value = activeResponse.isNotEmpty()

                    Log.d("TidakMampuViewModel", "Status pengajuan - Unverified: ${unverifiedResponse.size}, Active: ${activeResponse.size}")
                } else {
                    _hasActivePengajuan.value = false
                    _hasUnverifiedPengajuan.value = false
                    Log.e("TidakMampuViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("TidakMampuViewModel", "Gagal memeriksa status pengajuan: ${e.message}", e)
                _hasActivePengajuan.value = false
                _hasUnverifiedPengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}