package com.afiva.appskelurahan.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.KeteranganData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KeteranganViewModel : ViewModel() {
    private val _dataKeterangan = MutableStateFlow<KeteranganData?>(null)
    val dataKeterangan: StateFlow<KeteranganData?> = _dataKeterangan.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanDataKeterangan(data: KeteranganData) {
        _dataKeterangan.value = data
    }

    // Reset semua data
    fun resetData() {
        _dataKeterangan.value = KeteranganData(id_user = SessionManager.currentUser?.id)
    }

    // Memeriksa apakah ada pengajuan aktif untuk pengguna
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    val response = supabase.postgrest["KeteranganData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified") // Hanya cek unverified
                            }
                        }.decodeList<KeteranganData>()

                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("KeteranganViewModel", "Pengajuan aktif (unverified) untuk id_user $idUser: ${response.size} ditemukan")
                    Log.d("KeteranganViewModel", "Data pengajuan: $response")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("KeteranganViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("KeteranganViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}, StackTrace: ${e.stackTraceToString()}", e)
                _hasActivePengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Mengajukan data keterangan ke Supabase
    fun submitPengajuan(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = _dataKeterangan.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    supabase.postgrest["KeteranganData"].insert(insertData)
                    onSuccess()
                } else {
                    onError("Data keterangan tidak lengkap")
                }
            } catch (e: Exception) {
                Log.e("KeteranganViewModel", "Gagal mengajukan: ${e.message}", e)
                onError("Gagal mengajukan: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}