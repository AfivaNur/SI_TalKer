package com.afiva.appskelurahan

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.model.KeteranganData
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.model.TidakMampuData
import com.afiva.appskelurahan.model.UsahaData
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import android.util.Log
import com.afiva.appskelurahan.model.RiwayatSuratItem
import java.text.SimpleDateFormat
import java.util.Locale

// Fungsi untuk mengunggah file ke Supabase Storage
suspend fun uploadToSupabaseSDK(
    context: Context,
    nama: String, // Parameter 'nama' tidak digunakan dalam fungsi ini, mungkin untuk keperluan metadata?
    fileUri: Uri
): String {
    val contentResolver = context.contentResolver
    val supabase = SupabaseProvider.client
    val mimeType = contentResolver.getType(fileUri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "bin"
    val fileName = "uploads/${System.currentTimeMillis()}_file.$extension"
    val bucket = supabase.storage.from("filepengantar")

    val inputStream = contentResolver.openInputStream(fileUri)
    if (inputStream == null) {
        Log.e("SupabaseUpload", "Input stream is null for URI: $fileUri")
        return ""
    }

    return try {
        val fileBytes = inputStream.readBytes()
        inputStream.close()

        bucket.upload(fileName, fileBytes)
        val publicUrl = "${supabase.supabaseUrl}/storage/v1/object/public/filepengantar/$fileName"
        Log.d("SupabaseUpload", "File uploaded successfully: $publicUrl")
        publicUrl
    } catch (e: Exception) {
        Log.e("SupabaseUpload", "Error uploading file: ${e.message}", e)
        ""
    }
}

// Fungsi helper untuk mendapatkan nama file dari URI
fun getFileNameFromUri(context: Context, uri: Uri): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex("_display_name")
        it.moveToFirst()
        it.getString(nameIndex)
    } ?: uri.lastPathSegment ?: "file.tidakdiketahui"
}

// Fungsi untuk test individual query - debugging
suspend fun testIndividualQuery() = withContext(Dispatchers.IO) {
    val client = SupabaseProvider.client

    Log.d("TestQuery", "=== Testing Individual Queries ===")

    try {
        val pengantarData = client.from("SuratPengantarRTData").select().decodeList<SuratPengantarRTData>()
        Log.d("TestQuery", "SuratPengantarRTData count: ${pengantarData.size}")
        pengantarData.forEach { Log.d("TestQuery", "PengantarRT: id=${it.id}, nama=${it.namaPengantar}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error SuratPengantarRTData: ${e.message}", e)
    }

    try {
        val domisiliData = client.from("DomisiliData").select().decodeList<DomisiliData>()
        Log.d("TestQuery", "DomisiliData count: ${domisiliData.size}")
        domisiliData.forEach { Log.d("TestQuery", "Domisili: id=${it.id}, nama=${it.nama}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error DomisiliData: ${e.message}", e)
    }

    try {
        val tidakMampuData = client.from("TidakMampuData").select().decodeList<TidakMampuData>()
        Log.d("TestQuery", "TidakMampuData count: ${tidakMampuData.size}")
        tidakMampuData.forEach { Log.d("TestQuery", "TidakMampu: id=${it.id}, nama=${it.nama}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error TidakMampuData: ${e.message}", e)
    }

    try {
        val usahaData = client.from("UsahaData").select().decodeList<UsahaData>()
        Log.d("TestQuery", "UsahaData count: ${usahaData.size}")
        usahaData.forEach { Log.d("TestQuery", "Usaha: id=${it.id}, nama=${it.namaUsaha}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error UsahaData: ${e.message}", e)
    }

    try {
        val pindahData = client.from("PindahDatangData").select().decodeList<PindahDatangData>()
        Log.d("TestQuery", "PindahDatangData count: ${pindahData.size}")
        pindahData.forEach { Log.d("TestQuery", "Pindah: id=${it.id}, nama=${it.namaKepalaKeluargaAsal}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error PindahDatangData: ${e.message}", e)
    }

    try {
        val keteranganData = client.from("KeteranganData").select().decodeList<KeteranganData>()
        Log.d("TestQuery", "KeteranganData count: ${keteranganData.size}")
        keteranganData.forEach { Log.d("TestQuery", "Keterangan: id=${it.id}, nama=${it.nama}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error KeteranganData: ${e.message}", e)
    }

    try {
        val kematianData = client.from("KematianData").select().decodeList<KematianData>()
        Log.d("TestQuery", "KematianData count: ${kematianData.size}")
        kematianData.forEach { Log.d("TestQuery", "Kematian: id=${it.id}, nama=${it.namaJenazah}") }
    } catch (e: Exception) {
        Log.e("TestQuery", "Error KematianData: ${e.message}", e)
    }

    Log.d("TestQuery", "=== End Testing Individual Queries ===")
}

// Fungsi untuk mengambil semua riwayat surat dari berbagai tabel di Supabase - VERSI PERBAIKAN
suspend fun getRiwayatSurat(): List<RiwayatSuratItem> = withContext(Dispatchers.IO) {
    val client = SupabaseProvider.client
    val allRiwayat = mutableListOf<RiwayatSuratItem>()
    val currentUser = SessionManager.currentUser

    Log.d("RiwayatSurat", "=== Starting getRiwayatSurat ===")

    try {
        coroutineScope {
            // Fetch SuratPengantarRTData
            val pengantarRTDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching SuratPengantarRTData...")
                    val rawData = client.from("SuratPengantarRTData").select().decodeList<SuratPengantarRTData>()
                    Log.d("RiwayatSurat", "SuratPengantarRTData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.namaPengantar == null) {
                            Log.w("RiwayatSurat", "Skipping invalid SuratPengantarRTData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping PengantarRT: id=${data.id}, nama=${data.namaPengantar}, created_at=${data.created_at}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Pengantar RT",
                                    namaTabel = "SuratPengantarRTData",
                                    namaRiwayat = data.namaPengantar,
                                    tanggalPengajuan = data.created_at ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map PengantarRT data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching SuratPengantarRTData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch DomisiliData
            val domisiliDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching DomisiliData...")
                    val rawData = client.from("DomisiliData").select().decodeList<DomisiliData>()
                    Log.d("RiwayatSurat", "DomisiliData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.nama == null) {
                            Log.w("RiwayatSurat", "Skipping invalid DomisiliData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping Domisili: id=${data.id}, nama=${data.nama}, createdAt=${data.createdAt}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Domisili",
                                    namaTabel = "DomisiliData",
                                    namaRiwayat = data.nama,
                                    tanggalPengajuan = data.createdAt ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map Domisili data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching DomisiliData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch TidakMampuData
            val tidakMampuDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching TidakMampuData...")
                    val rawData = client.from("TidakMampuData").select().decodeList<TidakMampuData>()
                    Log.d("RiwayatSurat", "TidakMampuData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.nama == null) {
                            Log.w("RiwayatSurat", "Skipping invalid TidakMampuData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping TidakMampu: id=${data.id}, nama=${data.nama}, created_at=${data.created_at}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Tidak Mampu",
                                    namaTabel = "TidakMampuData",
                                    namaRiwayat = data.nama,
                                    tanggalPengajuan = data.created_at ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map TidakMampu data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching TidakMampuData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch UsahaData
            val usahaDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching UsahaData...")
                    val rawData = client.from("UsahaData").select().decodeList<UsahaData>()
                    Log.d("RiwayatSurat", "UsahaData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.namaUsaha == null) {
                            Log.w("RiwayatSurat", "Skipping invalid UsahaData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping Usaha: id=${data.id}, namaUsaha=${data.namaUsaha}, created_at=${data.created_at}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Usaha",
                                    namaTabel = "UsahaData",
                                    namaRiwayat = data.namaUsaha,
                                    tanggalPengajuan = data.created_at ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map Usaha data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching UsahaData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch PindahDatangData
            val pindahDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching PindahDatangData...")
                    val rawData = client.from("PindahDatangData").select().decodeList<PindahDatangData>()
                    Log.d("RiwayatSurat", "PindahDatangData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.namaKepalaKeluargaAsal == null) {
                            Log.w("RiwayatSurat", "Skipping invalid PindahDatangData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping Pindah: id=${data.id}, namaKepalaKeluargaAsal=${data.namaKepalaKeluargaAsal}, created_at=${data.created_at}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Pindah Datang",
                                    namaTabel = "PindahDatangData",
                                    namaRiwayat = data.namaKepalaKeluargaAsal,
                                    tanggalPengajuan = data.created_at ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map Pindah data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching PindahDatangData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch KeteranganData
            val keteranganDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching KeteranganData...")
                    val rawData = client.from("KeteranganData").select().decodeList<KeteranganData>()
                    Log.d("RiwayatSurat", "KeteranganData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.nama == null) {
                            Log.w("RiwayatSurat", "Skipping invalid KeteranganData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping Keterangan: id=${data.id}, nama=${data.nama}, created_at=${data.createdAt}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan",
                                    namaTabel = "KeteranganData",
                                    namaRiwayat = data.nama,
                                    tanggalPengajuan = data.createdAt ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map Keterangan data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching KeteranganData: ${e.message}", e)
                    emptyList()
                }
            }

            // Fetch KematianData
            val kematianDeferred = async {
                try {
                    Log.d("RiwayatSurat", "Fetching KematianData...")
                    val rawData = client.from("KematianData").select().decodeList<KematianData>()
                    Log.d("RiwayatSurat", "KematianData raw count: ${rawData.size}")
                    rawData.mapNotNull { data ->
                        if (data.id == null || data.namaJenazah == null) {
                            Log.w("RiwayatSurat", "Skipping invalid KematianData: $data")
                            null
                        } else {
                            try {
                                Log.d("RiwayatSurat", "Mapping Kematian: id=${data.id}, namaJenazah=${data.namaJenazah}, created_at=${data.created_at}, status=${data.status}")
                                RiwayatSuratItem(
                                    id = data.id.toInt() ?: throw IllegalArgumentException("Invalid ID format: ${data.id}"),
                                    jenisSurat = "Keterangan Kematian",
                                    namaTabel = "KematianData",
                                    namaRiwayat = data.namaJenazah,
                                    tanggalPengajuan = data.created_at ?: "",
                                    status = data.status ?: ""
                                )
                            } catch (e: Exception) {
                                Log.w("RiwayatSurat", "Failed to map Kematian data: $data, error: ${e.message}")
                                null
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Error fetching KematianData: ${e.message}", e)
                    emptyList()
                }
            }

            // Collect all results
            Log.d("RiwayatSurat", "Awaiting all deferred results...")
            val pengantarRTResult = pengantarRTDeferred.await()
            Log.d("RiwayatSurat", "PengantarRT final result: ${pengantarRTResult.size} items")
            val domisiliResult = domisiliDeferred.await()
            Log.d("RiwayatSurat", "Domisili final result: ${domisiliResult.size} items")
            val tidakMampuResult = tidakMampuDeferred.await()
            Log.d("RiwayatSurat", "TidakMampu final result: ${tidakMampuResult.size} items")
            val usahaResult = usahaDeferred.await()
            Log.d("RiwayatSurat", "Usaha final result: ${usahaResult.size} items")
            val pindahResult = pindahDeferred.await()
            Log.d("RiwayatSurat", "Pindah final result: ${pindahResult.size} items")
            val keteranganResult = keteranganDeferred.await()
            Log.d("RiwayatSurat", "Keterangan final result: ${keteranganResult.size} items")
            val kematianResult = kematianDeferred.await()
            Log.d("RiwayatSurat", "Kematian final result: ${kematianResult.size} items")

            // Combine all results
            allRiwayat.addAll(pengantarRTResult)
            allRiwayat.addAll(domisiliResult)
            allRiwayat.addAll(tidakMampuResult)
            allRiwayat.addAll(usahaResult)
            allRiwayat.addAll(pindahResult)
            allRiwayat.addAll(keteranganResult)
            allRiwayat.addAll(kematianResult)

            Log.d("RiwayatSurat", "Combined riwayat before sorting: ${allRiwayat.size} items")

            // Log each item before sorting
            allRiwayat.forEachIndexed { index, item ->
                Log.d("RiwayatSurat", "Item $index: ${item.jenisSurat} - ${item.namaRiwayat} - ${item.tanggalPengajuan}")
            }

            // Sort by tanggalPengajuan
            val sortedRiwayat = allRiwayat.sortedByDescending { item ->
                try {
                    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(item.tanggalPengajuan)
                    Log.d("RiwayatSurat", "Sorting item: ${item.jenisSurat} with date: ${item.tanggalPengajuan}")
                    date?.time ?: 0L
                } catch (e: Exception) {
                    Log.e("RiwayatSurat", "Invalid date format for ${item.jenisSurat}: ${item.tanggalPengajuan}")
                    0L
                }
            }

            Log.d("RiwayatSurat", "=== Final sorted riwayat: ${sortedRiwayat.size} items ===")
            // Log final result
            sortedRiwayat.forEachIndexed { index, item ->
                Log.d("RiwayatSurat", "Final[$index]: ${item.jenisSurat} - ${item.namaRiwayat} - ${item.tanggalPengajuan}")
            }

            sortedRiwayat
        }
    } catch (e: Exception) {
        Log.e("RiwayatSurat", "General error in getRiwayatSurat: ${e.message}", e)
        emptyList()
    }
}
// Fungsi alternatif dengan pendekatan sequential untuk debugging
suspend fun getRiwayatSuratSequential(): List<RiwayatSuratItem> = withContext(Dispatchers.IO) {
    val client = SupabaseProvider.client
    val allRiwayat = mutableListOf<RiwayatSuratItem>()

    Log.d("RiwayatSurat", "=== Starting Sequential Fetch ===")

    try {
        // 1. Fetch SuratPengantarRTData
        try {
            val pengantarData = client.from("SuratPengantarRTData").select().decodeList<SuratPengantarRTData>()
            Log.d("RiwayatSurat", "Sequential - PengantarRT: ${pengantarData.size} items")
            pengantarData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan Pengantar RT",
                    namaTabel = "SuratPengantarRTData",
                    namaRiwayat = it.namaPengantar,
                    tanggalPengajuan = it.created_at ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - PengantarRT: ${e.message}")
        }

        // 2. Fetch DomisiliData
        try {
            val domisiliData = client.from("DomisiliData").select().decodeList<DomisiliData>()
            Log.d("RiwayatSurat", "Sequential - Domisili: ${domisiliData.size} items")
            domisiliData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan Domisili",
                    namaTabel = "DomisiliData",
                    namaRiwayat = it.nama,
                    tanggalPengajuan = it.createdAt ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - Domisili: ${e.message}")
        }

        // 3. Fetch TidakMampuData
        try {
            val tidakMampuData = client.from("TidakMampuData").select().decodeList<TidakMampuData>()
            Log.d("RiwayatSurat", "Sequential - TidakMampu: ${tidakMampuData.size} items")
            tidakMampuData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan Tidak Mampu",
                    namaTabel = "TidakMampuData",
                    namaRiwayat = it.nama,
                    tanggalPengajuan = it.created_at ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - TidakMampu: ${e.message}")
        }

        // 4. Fetch UsahaData
        try {
            val usahaData = client.from("UsahaData").select().decodeList<UsahaData>()
            Log.d("RiwayatSurat", "Sequential - Usaha: ${usahaData.size} items")
            usahaData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id ?: -1,
                    jenisSurat = "Keterangan Usaha",
                    namaTabel = "UsahaData",
                    namaRiwayat = it.namaUsaha,
                    tanggalPengajuan = it.created_at ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - Usaha: ${e.message}")
        }

        // 5. Fetch PindahDatangData
        try {
            val pindahData = client.from("PindahDatangData").select().decodeList<PindahDatangData>()
            Log.d("RiwayatSurat", "Sequential - Pindah: ${pindahData.size} items")
            pindahData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan Pindah Datang",
                    namaTabel = "PindahDatangData",
                    namaRiwayat = it.namaKepalaKeluargaAsal,
                    tanggalPengajuan = it.created_at ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - Pindah: ${e.message}")
        }

        // 6. Fetch KeteranganData
        try {
            val keteranganData = client.from("KeteranganData").select().decodeList<KeteranganData>()
            Log.d("RiwayatSurat", "Sequential - Keterangan: ${keteranganData.size} items")
            keteranganData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan",
                    namaTabel = "KeteranganData",
                    namaRiwayat = it.nama.toString(),
                    tanggalPengajuan = it.createdAt ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - Keterangan: ${e.message}")
        }

        // 7. Fetch KematianData
        try {
            val kematianData = client.from("KematianData").select().decodeList<KematianData>()
            Log.d("RiwayatSurat", "Sequential - Kematian: ${kematianData.size} items")
            kematianData.forEach {
                allRiwayat.add(RiwayatSuratItem(
                    id = it.id,
                    jenisSurat = "Keterangan Kematian",
                    namaTabel = "KematianData",
                    namaRiwayat = it.namaJenazah,
                    tanggalPengajuan = it.created_at ?: "",
                    status = it.status ?: ""
                ))
            }
        } catch (e: Exception) {
            Log.e("RiwayatSurat", "Sequential error - Kematian: ${e.message}")
        }

        Log.d("RiwayatSurat", "Sequential - Total collected: ${allRiwayat.size} items")

        // Sort by date
        val sortedRiwayat = allRiwayat.sortedByDescending { it.tanggalPengajuan }
        Log.d("RiwayatSurat", "Sequential - Final sorted: ${sortedRiwayat.size} items")

        return@withContext sortedRiwayat

    } catch (e: Exception) {
        Log.e("RiwayatSurat", "Sequential general error: ${e.message}", e)
        return@withContext emptyList()
    }
}