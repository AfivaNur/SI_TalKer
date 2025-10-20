package com.afiva.appskelurahan.v_model

import androidx.lifecycle.ViewModel
import com.afiva.appskelurahan.model.SuratPengantarRTData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SuratPengantarRTViewModel : ViewModel() {

    private val _dataSuratPengantarRT = MutableStateFlow<SuratPengantarRTData?>(null)
    val dataSuratPengantarRT: StateFlow<SuratPengantarRTData?> = _dataSuratPengantarRT.asStateFlow()

    // Simpan seluruh data
    fun simpanDataSuratPengantarRT(data: SuratPengantarRTData) {
        _dataSuratPengantarRT.value = data
    }

//    // Update data dengan copy untuk field tertentu
//    fun updateDataSuratPengantarRT(updater: SuratPengantarRTData.() -> SuratPengantarRTData) {
//        _dataSuratPengantarRT.value = _dataSuratPengantarRT.value?.updater()
//            ?: SuratPengantarRTData().updater()
//    }

    // Reset data hanya setelah pengajuan selesai
    fun resetData() {
        _dataSuratPengantarRT.value = null // Atau SuratPengantarRTData() jika ingin default kosong
    }
}