package com.afiva.appskelurahan.data.dto.detaildata

import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.model.KeteranganData
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.model.TidakMampuData
import com.afiva.appskelurahan.model.UsahaData
import kotlinx.serialization.Serializable

@Serializable
sealed class DetailData {
    @Serializable
    data class SuratPengantar(val data: SuratPengantarRTData) : DetailData()

    @Serializable
    data class Domisili(val data: DomisiliData) : DetailData()

    @Serializable
    data class Keterangan(val data: KeteranganData) : DetailData()

    @Serializable
    data class Kematian(val data: KematianData) : DetailData()

    @Serializable
    data class TidakMampu(val data: TidakMampuData) : DetailData()

    @Serializable
    data class Usaha(val data: UsahaData) : DetailData()

    @Serializable
    data class PindahDatang(val data: PindahDatangData) : DetailData()
}