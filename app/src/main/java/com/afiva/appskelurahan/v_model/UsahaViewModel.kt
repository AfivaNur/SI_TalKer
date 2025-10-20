package com.afiva.appskelurahan.v_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.UsahaData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsahaViewModel : ViewModel() {

    private val _dataUsaha = MutableStateFlow<UsahaData?>(null)
    val dataUsaha: StateFlow<UsahaData?> = _dataUsaha.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    private val _hasUnverifiedPengajuan = mutableStateOf(false)
    val hasUnverifiedPengajuan: State<Boolean> = _hasUnverifiedPengajuan

    private val _isAjukanEnabled = mutableStateOf(true)
    val isAjukanEnabled: State<Boolean> = _isAjukanEnabled

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanDataUsaha(data: UsahaData) {
        _dataUsaha.value = data
    }

    fun resetData() {
        val idUser = SessionManager.currentUser?.id ?: -1
        _dataUsaha.value = UsahaData(id_user = idUser)
    }

    // Fungsi untuk mengecek pengajuan aktif (selain unverified dan rejected)
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    // Cek pengajuan yang sedang diproses (bukan unverified dan rejected)
                    val response = supabase.postgrest["UsahaData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<UsahaData>()
                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("UsahaViewModel", "Pengajuan aktif (unverified) untuk id_user $idUser: ${response.size} ditemukan")
                    Log.d("UsahaViewModel", "Data pengajuan: $response")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("UsahaViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("UsahaViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}, StackTrace: ${e.stackTraceToString()}", e)
                _hasActivePengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk mengecek pengajuan unverified
    fun checkUnverifiedPengajuan(idUser: String?) {
        viewModelScope.launch {
            try {
                if (idUser != null) {
                    val response = supabase.postgrest["UsahaData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<UsahaData>()
                    _hasUnverifiedPengajuan.value = response.isNotEmpty()
                    Log.d("UsahaViewModel", "Pengajuan unverified untuk id_user $idUser: ${response.size} ditemukan")
                } else {
                    _hasUnverifiedPengajuan.value = false
                    Log.e("UsahaViewModel", "ID pengguna tidak tersedia untuk cek unverified")
                }
            } catch (e: Exception) {
                Log.e("UsahaViewModel", "Gagal memeriksa pengajuan unverified: ${e.message}", e)
                _hasUnverifiedPengajuan.value = false
            }
        }
    }

    // Fungsi untuk submit pengajuan usaha
    fun submitPengajuan(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = _dataUsaha.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    supabase.postgrest["UsahaData"].insert(insertData)
                    Log.d("UsahaViewModel", "Berhasil mengajukan data usaha")
                    onSuccess()
                } else {
                    onError("Data usaha tidak lengkap")
                }
            } catch (e: Exception) {
                Log.e("UsahaViewModel", "Gagal mengajukan: ${e.message}", e)
                onError("Gagal mengajukan: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Fungsi untuk mengecek semua status pengajuan sekaligus
    fun checkAllPengajuanStatus(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    // Cek unverified
                    val unverifiedResponse = supabase.postgrest["UsahaData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<UsahaData>()
                    _hasUnverifiedPengajuan.value = unverifiedResponse.isNotEmpty()

                    // Cek active (processing, approved, dll)
                    val activeResponse = supabase.postgrest["UsahaData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<UsahaData>()
                    _hasActivePengajuan.value = activeResponse.isNotEmpty()

                    Log.d("UsahaViewModel", "Status pengajuan - Unverified: ${unverifiedResponse.size}, Active: ${activeResponse.size}")
                } else {
                    _hasActivePengajuan.value = false
                    _hasUnverifiedPengajuan.value = false
                    Log.e("UsahaViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("UsahaViewModel", "Gagal memeriksa status pengajuan: ${e.message}", e)
                _hasActivePengajuan.value = false
                _hasUnverifiedPengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}