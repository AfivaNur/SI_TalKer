package com.afiva.appskelurahan.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.KematianData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KematianViewModel : ViewModel() {

    private val _dataKematian = MutableStateFlow<KematianData?>(null)
    val dataKematian: StateFlow<KematianData?> = _dataKematian.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    private val _hasUnverifiedPengajuan = mutableStateOf(false)
    val hasUnverifiedPengajuan: State<Boolean> = _hasUnverifiedPengajuan

    private val _isAjukanEnabled = mutableStateOf(true)
    val isAjukanEnabled: State<Boolean> = _isAjukanEnabled

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanDataKematian(data: KematianData) {
        _dataKematian.value = data
    }

    fun resetData() {
        val idUser = SessionManager.currentUser?.id ?: -1
        _dataKematian.value = KematianData(id_user = idUser)
    }

    // Fungsi untuk mengecek pengajuan aktif (selain unverified dan rejected)
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    // Cek pengajuan yang sedang diproses (bukan unverified dan rejected)
                    val response = supabase.postgrest["KematianData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<KematianData>()
                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("KematianViewModel", "Pengajuan aktif untuk id_user $idUser: ${response.size} ditemukan")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("KematianViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("KematianViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}", e)
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
                    val response = supabase.postgrest["KematianData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<KematianData>()
                    _hasUnverifiedPengajuan.value = response.isNotEmpty()
                    Log.d("KematianViewModel", "Pengajuan unverified untuk id_user $idUser: ${response.size} ditemukan")
                } else {
                    _hasUnverifiedPengajuan.value = false
                    Log.e("KematianViewModel", "ID pengguna tidak tersedia untuk cek unverified")
                }
            } catch (e: Exception) {
                Log.e("KematianViewModel", "Gagal memeriksa pengajuan unverified: ${e.message}", e)
                _hasUnverifiedPengajuan.value = false
            }
        }
    }

    // Fungsi untuk submit pengajuan kematian
    fun submitPengajuan(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = _dataKematian.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    supabase.postgrest["KematianData"].insert(insertData)
                    Log.d("KematianViewModel", "Berhasil mengajukan data kematian")
                    onSuccess()
                } else {
                    onError("Data kematian tidak lengkap")
                }
            } catch (e: Exception) {
                Log.e("KematianViewModel", "Gagal mengajukan: ${e.message}", e)
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
                    val unverifiedResponse = supabase.postgrest["KematianData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified")
                            }
                        }.decodeList<KematianData>()
                    _hasUnverifiedPengajuan.value = unverifiedResponse.isNotEmpty()

                    // Cek active (processing, approved, dll)
                    val activeResponse = supabase.postgrest["KematianData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                neq("status", "unverified")
                                neq("status", "rejected")
                            }
                        }.decodeList<KematianData>()
                    _hasActivePengajuan.value = activeResponse.isNotEmpty()

                    Log.d("KematianViewModel", "Status pengajuan - Unverified: ${unverifiedResponse.size}, Active: ${activeResponse.size}")
                } else {
                    _hasActivePengajuan.value = false
                    _hasUnverifiedPengajuan.value = false
                    Log.e("KematianViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("KematianViewModel", "Gagal memeriksa status pengajuan: ${e.message}", e)
                _hasActivePengajuan.value = false
                _hasUnverifiedPengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}