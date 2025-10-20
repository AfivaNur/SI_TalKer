package com.afiva.appskelurahan.v_model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.PindahDatangData
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PindahDatangViewModel : ViewModel() {
    private val _dataPindahDatang = MutableStateFlow<PindahDatangData?>(null)
    val dataPindahDatang: StateFlow<PindahDatangData?> = _dataPindahDatang.asStateFlow()

    private val _hasActivePengajuan = mutableStateOf(false)
    val hasActivePengajuan: State<Boolean> = _hasActivePengajuan

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val supabase = SupabaseProvider.client

    fun simpanPindahDatangData(data: PindahDatangData) {
        _dataPindahDatang.value = data
    }

    // Reset semua data
    fun resetData() {
        _dataPindahDatang.value = PindahDatangData(id_user = SessionManager.currentUser?.id ?: -1)
    }

    // Memeriksa apakah ada pengajuan aktif untuk pengguna
    fun checkActivePengajuan(idUser: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (idUser != null) {
                    val response = supabase.postgrest["PindahDatangData"]
                        .select {
                            filter {
                                eq("id_user", idUser)
                                eq("status", "unverified") // Hanya cek unverified
                            }
                        }.decodeList<PindahDatangData>()

                    _hasActivePengajuan.value = response.isNotEmpty()
                    Log.d("PindahDatangViewModel", "Pengajuan aktif (unverified) untuk id_user $idUser: ${response.size} ditemukan")
                    Log.d("PindahDatangViewModel", "Data pengajuan: $response")
                } else {
                    _hasActivePengajuan.value = false
                    Log.e("PindahDatangViewModel", "ID pengguna tidak tersedia")
                }
            } catch (e: Exception) {
                Log.e("PindahDatangViewModel", "Gagal memeriksa pengajuan aktif: ${e.message}, StackTrace: ${e.stackTraceToString()}", e)
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
                val data = _dataPindahDatang.value
                if (data != null) {
                    val insertData = data.copy(
                        id_user = SessionManager.currentUser?.id ?: -1,
                        status = "unverified"
                    )
                    supabase.postgrest["PindahDatangData"].insert(insertData)
                    onSuccess()
                } else {
                    onError("Data domisili tidak lengkap")
                }
            } catch (e: Exception) {
                Log.e("PindahDatangViewModel", "Gagal mengajukan: ${e.message}", e)
                onError("Gagal mengajukan: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}