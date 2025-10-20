package com.afiva.appskelurahan.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.DomisiliData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DomisiliViewModel : ViewModel() {
    private val _dataDomisili = MutableStateFlow<DomisiliData?>(null)
    val dataDomisili: StateFlow<DomisiliData?> = _dataDomisili.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanDataDomisili(data: DomisiliData) {
        _dataDomisili.value = data
    }

    // Reset semua data
    fun resetData() {
        _dataDomisili.value = DomisiliData(id_user = SessionManager.currentUser?.id)
    }

    // Memeriksa apakah ada pengajuan aktif untuk pengguna
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    val response = supabase.postgrest["DomisiliData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified") // Hanya cek unverified
                            }
                        }.decodeList<DomisiliData>()

                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("DomisiliViewModel", "Pengajuan aktif (unverified) untuk id_user $idUser: ${response.size} ditemukan")
                    Log.d("DomisiliViewModel", "Data pengajuan: $response")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("DomisiliViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("DomisiliViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}, StackTrace: ${e.stackTraceToString()}", e)
                _hasActivePengajuan.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Mengajukan data domisili ke Supabase
    fun submitPengajuan(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val data = _dataDomisili.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    supabase.postgrest["DomisiliData"].insert(insertData)
                    onSuccess()
                } else {
                    onError("Data domisili tidak lengkap")
                }
            } catch (e: Exception) {
                Log.e("DomisiliViewModel", "Gagal mengajukan: ${e.message}", e)
                onError("Gagal mengajukan: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }
}