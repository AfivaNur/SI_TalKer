package com.afiva.appskelurahan.ui.screens

import java.util.Calendar
import java.util.Locale
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
// Import gambar logo

import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.data.dto.detaildata.DetailData
import com.afiva.appskelurahan.data.dto.detaildata.DetailWrapper
import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.model.KeteranganData
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.model.RiwayatSuratItem
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.model.TidakMampuData
import com.afiva.appskelurahan.model.UsahaData
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.routing.Screen.DetailRoute
import com.afiva.appskelurahan.v_model.RiwayatViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiwayatScreen(
    navController: NavController,
    viewModel: RiwayatViewModel = viewModel()

) {
    //  Blokir tombol "Back" HP agar tidak kembali
    BackHandler(enabled = true) {
        // Kosongkan atau beri logika konfirmasi jika perlu
    }

    val riwayatList by viewModel.riwayatList.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    val primaryColor = Color(0xFF00897B)
    val surfaceColor = Color(0xFFF8F9FA)

    val supabase = SupabaseProvider.client
    val coroutineScope = rememberCoroutineScope()
    val my_context = LocalContext.current
    val currentUser = SessionManager.currentUser

    LaunchedEffect(Unit) {
        viewModel.loadRiwayat()
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                primaryColor,
                                primaryColor.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .shadow(
                        elevation = 8.dp,
                        spotColor = primaryColor.copy(alpha = 0.3f)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Kembali",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        Color.White.copy(alpha = 0.15f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_banyuasin),
                                    contentDescription = "Logo Banyuasin",
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "RIWAYAT PENGAJUAN",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Pengantar RT dan Pelayanan",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            RiwayatBottomNavigation(
                navController = navController,
                primaryColor = primaryColor
            )
        },
        containerColor = surfaceColor
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        primaryColor.copy(alpha = 0.1f),
                                        primaryColor.copy(alpha = 0.05f)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        primaryColor.copy(alpha = 0.2f),
                                        RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = primaryColor,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Riwayat Pengajuan Surat",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2C3E50)
                                )
                                Text(
                                    text = "Lihat status dan unduh surat yang telah selesai",
                                    fontSize = 13.sp,
                                    color = Color(0xFF7F8C8D),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = primaryColor)
                    }
                }
            } else if (riwayatList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = "Tidak ada data",
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Belum ada riwayat pengajuan.",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                items(riwayatList) { surat ->
                    val isAdminOrLurah = currentUser?.role == "admin" || currentUser?.role == "lurah"
                    val note = if (surat.status == "rejected" && !isAdminOrLurah) {
                        "Lengkapi berkas dan periksa kembali!"
                    } else {
                        null
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        RiwayatCard(
                            surat = surat,
                            primaryColor = primaryColor,
                            modifier = Modifier.fillMaxWidth(),
                            note = note,
                            onViewDetails = {
                                coroutineScope.launch {
                                    try {
                                        val result = when (surat.namaTabel) {
                                            "SuratPengantarRTData" -> {
                                                val data = supabase.from("SuratPengantarRTData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<SuratPengantarRTData>()
                                                DetailData.SuratPengantar(data)
                                            }
                                            "DomisiliData" -> {
                                                val data = supabase.from("DomisiliData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<DomisiliData>()
                                                DetailData.Domisili(data)
                                            }
                                            "KeteranganData" -> {
                                                val data = supabase.from("KeteranganData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<KeteranganData>()
                                                DetailData.Keterangan(data)
                                            }
                                            "KematianData" -> {
                                                val data = supabase.from("KematianData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<KematianData>()
                                                DetailData.Kematian(data)
                                            }
                                            "TidakMampuData" -> {
                                                val data = supabase.from("TidakMampuData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<TidakMampuData>()
                                                DetailData.TidakMampu(data)
                                            }
                                            "UsahaData" -> {
                                                val data = supabase.from("UsahaData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<UsahaData>()

                                                DetailData.Usaha(data)
                                            }
                                            "PindahDatangData" -> {
                                                val data = supabase.from("PindahDatangData")
                                                    .select {
                                                        filter {
                                                            eq("id", surat.id)
                                                            if (currentUser?.role != "admin") {
                                                                eq("id_user", currentUser?.id ?: "")
                                                            }
                                                        }
                                                    }
                                                    .decodeSingle<PindahDatangData>()
                                                DetailData.PindahDatang(data)
                                            }
                                            else -> null
                                        }

                                        result?.let { detailData ->
                                            val jsonData = Json.encodeToString(
                                                DetailWrapper.serializer(),
                                                DetailWrapper(surat.namaTabel, detailData)
                                            )

                                            navController.navigate(DetailRoute.createRoute(jsonData))
                                        } ?: run {
                                            Toast.makeText(
                                                my_context,
                                                "Data tidak ditemukan untuk ${surat.namaTabel}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    } catch (e: Exception) {
                                        Log.e("RiwayatCard", "Error: ${e.message}", e)
                                        Toast.makeText(my_context, "Gagal memuat detail: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            onAccept = {
                                coroutineScope.launch {
                                    val updateResult = supabase
                                        .postgrest[surat.namaTabel]
                                        .update(
                                            {
                                                set("status", "verified")
                                                set("verified_at", "now()")
                                                // set("id_verifikator", SessionManager.currentUser?.id)
                                            }
                                        ) {
                                            filter {
                                                eq("id", surat.id)
                                            }
                                        }
                                    viewModel.loadRiwayat()
                                }
                            },
                            onReject = {
                                coroutineScope.launch {
                                    val updateResult = supabase
                                        .postgrest[surat.namaTabel]
                                        .update(
                                            {
                                                set("status", "rejected")
                                            }
                                        ) {
                                            filter {
                                                eq("id", surat.id)
                                            }
                                        }
                                    viewModel.loadRiwayat()
                                }
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RiwayatCard(
    surat: RiwayatSuratItem,
    primaryColor: Color,
    modifier: Modifier = Modifier,
    onViewDetails: () -> Unit,
    onAccept: () -> Unit, // Callback untuk verifikasi diterima
    onReject: () -> Unit,  // Callback untuk verifikasi ditolak
    note: String?
) {
    val context = LocalContext.current
    val supabase = SupabaseProvider.client
    val coroutineScope = rememberCoroutineScope()
    val currentUser = SessionManager.currentUser

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            primaryColor.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "Pernyataan",
                        tint = primaryColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        surat.jenisSurat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF2C3E50)
                    )
                    Text(
                        "Nama: ${surat.namaRiwayat}",
                        modifier = Modifier.padding(top = 4.dp),
                        fontSize = 13.sp,
                        color = Color(0xFF7F8C8D)
                    )
                    Text(
                        "Diajukan: ${surat.tanggalPengajuan}",
                        fontSize = 13.sp,
                        color = Color(0xFF7F8C8D)
                    )
                    // Tambahan teks pembeda berdasarkan role
                    if (SessionManager.currentUser?.role == "admin") {
                        Text(
                            text = "Mode: Admin",
                            fontSize = 12.sp,
                            color = primaryColor,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    } else {
                        Text(
                            text = "Mode: Pengguna",
                            fontSize = 12.sp,
                            color = Color(0xFF7F8C8D),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                StatusChip(status = surat.status)
            }
            // Tampilkan catatan jika ada
            note?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = it,
                    color = Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

// Tombol centang dan silang
            if (surat.status.trim() == "unverified" && SessionManager.currentUser?.role == "admin") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (currentUser?.role == "admin") {
                        IconButton(
                            onClick = onAccept
                        ) {
                            Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = "Verifikasi Terima",
                                tint = Color(0xFF2ECC71), // Hijau
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = onReject
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Verifikasi Tolak",
                                tint = Color(0xFFE74C3C), // Merah
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

// Teks pemberitahuan sederhana untuk user selain admin dan lurah
            if (surat.status.trim() == "unverified" && currentUser?.role != "admin" && currentUser?.role != "lurah") {
                val estimatedCompletion = LocalDateTime.now().plusDays(1)
                val formattedTime = estimatedCompletion.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.waktu),
                        contentDescription = "Waktu Tunggu",
                        tint = Color(0xFF3498DB), // Biru
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Sedang Diproses, Selesai Sekitar: $formattedTime",
                        color = Color(0xFF3498DB), // Biru
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                color = Color(0xFFECF0F1),
                thickness = 1.dp
            )

            if (currentUser?.role != "lurah") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (surat.status == "verified") {
                        Button(
                            onClick = { /* Download action */
                                if (surat.namaTabel == "DomisiliData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["DomisiliData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<DomisiliData>()

                                        if (result.isNotEmpty()) {
                                            var dataDomisili: DomisiliData = result.first()
                                            generatePdfDomisili(
                                                context = context,
                                                data = dataDomisili,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_Domisili.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                                if (surat.namaTabel == "KeteranganData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["KeteranganData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<KeteranganData>()

                                        if (result.isNotEmpty()) {
                                            val dataKeterangan: KeteranganData = result.first()
                                            generatePdfKeterangan(
                                                context = context,
                                                data = dataKeterangan,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_Keterangan.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                                if (surat.namaTabel == "KematianData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["KematianData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<KematianData>()

                                        if (result.isNotEmpty()) {
                                            val dataKematian: KematianData = result.first()
                                            generatePdfKematian(
                                                context = context,
                                                data = dataKematian,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_KeteranganKematian.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                                if (surat.namaTabel == "TidakMampuData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["TidakMampuData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<TidakMampuData>()

                                        if (result.isNotEmpty()) {
                                            val dataTidakMampu: TidakMampuData = result.first()
                                            generatePdfTidakMampu(
                                                context = context,
                                                data = dataTidakMampu,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_TidakMampu.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                                if (surat.namaTabel == "SuratPengantarRTData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["SuratPengantarRTData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<SuratPengantarRTData>()

                                        if (result.isNotEmpty()) {
                                            val dataSuratPengantarRT: SuratPengantarRTData =
                                                result.first()
                                            generatePdfSuratPengantarRT(
                                                context = context,
                                                data = dataSuratPengantarRT,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_PengantarRT.pdf"
                                            )
                                        } else {
                                        }
                                    }

                                }
                                if (surat.namaTabel == "UsahaData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["UsahaData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<UsahaData>()

                                        if (result.isNotEmpty()) {
                                            val dataUsaha: UsahaData = result.first()
                                            generatePdfUsaha(
                                                context = context,
                                                data = dataUsaha,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_KeteranganUsaha.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                                if (surat.namaTabel == "PindahDatangData") {
                                    coroutineScope.launch {
                                        val result = supabase
                                            .postgrest["PindahDatangData"]
                                            .select {
                                                filter {
                                                    eq("id", surat.id)
                                                }
                                            }.decodeList<PindahDatangData>()

                                        if (result.isNotEmpty()) {
                                            val dataPindahDatang: PindahDatangData = result.first()
                                            generatePdfPindahDatang(
                                                context = context,
                                                data = dataPindahDatang,
                                                filename = System.currentTimeMillis()
                                                    .toString() + "_PindahDatang.pdf"
                                            )
                                        } else {
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.unduh),
                                contentDescription = "Download",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Unduh Surat",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Button(
                        onClick = onViewDetails,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor.copy(alpha = 0.8f),
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Lihat Detail",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Lihat Detail",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val (backgroundColor, textColor) = when (status) {
        "verified" -> Color(0xFF4CAF50) to Color.White
        "unverified" -> Color.LightGray to Color.DarkGray
        "rejected" -> Color(0xFFF44336) to Color.White
        else -> Color.LightGray to Color.DarkGray
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = status,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RiwayatBottomNavigation(
    navController: NavController,
    primaryColor: Color
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.shadow(
            elevation = 8.dp,
            spotColor = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        val items = listOf(
            Screen.Beranda,
            Screen.RiwayatScreen,
            Screen.InfoKelurahanScreen,
            Screen.ProfilScreen
        )
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    val iconPainter = when (screen) {
                        Screen.Beranda -> painterResource(R.drawable.beranda_menu)
                        Screen.RiwayatScreen -> painterResource(R.drawable.riwayat)
                        Screen.InfoKelurahanScreen -> painterResource(R.drawable.tentang_kelurahan)
                        Screen.ProfilScreen -> painterResource(R.drawable.profil_menu)
                        else -> Icons.Default.Info
                    }
                    Icon(
                        painter = iconPainter as Painter,
                        contentDescription = screen.route,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                },
                label = { Text(screen.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryColor,
                    selectedTextColor = primaryColor,
                    unselectedIconColor = Color(0xFF9E9E9E),
                    unselectedTextColor = Color(0xFF9E9E9E),
                    indicatorColor = primaryColor.copy(alpha = 0.1f)
                )
            )
        }
    }
}

//PENGANTAR RT
fun generatePdfSuratPengantarRT(
    context: Context,
    data: SuratPengantarRTData,
    filename: String = "surat_pengantar_rt.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop

//gambar logo
    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

// 2. Posisi teks header di tengah logo
    val textStartY = y + 25f // posisi vertikal teks agar rata tengah logo
    y = textStartY

    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KETUA RUKUN TETANGGA"
    val line4 = "SUMATERA SELATAN"

    val centerX1 = (canvas.width - paint.measureText(line1)) / 2
    canvas.drawText(line1, centerX1, y, paint); y += 20f

    val centerX2 = (canvas.width - paint.measureText(line2)) / 2
    canvas.drawText(line2, centerX2, y, paint); y += 20f

    val centerX3 = (canvas.width - paint.measureText(line3)) / 2
    canvas.drawText(line3, centerX3, y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 15f

    val centerX4 = (canvas.width - paint.measureText(line4)) / 2
    canvas.drawText(line4, centerX4, y, paint); y += 20f

    paint.strokeWidth = 2f
    // Header GARIS
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT PENGANTAR RT"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2

    canvas.drawText(title, titleX, y, paint)
    y += 5f

// Garis dengan panjang & posisi sama seperti teks "SURAT PENGANTAR RT"
    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor :      145 / ... / ${data.rtPengantar}/ KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    val centerX = (canvas.width - textWidth) / 2
    canvas.drawText(nomorText, centerX, y, paint); y += 30f


    val marginRight = 40f // misalnya margin kanan 40 dp

    paint.typeface = bold
    paint.textSize = 12f
    val labelList = listOf(
        "a. Nama", "b. Jabatan", "Nama", "TTL", "NIK", "Jenis Kelamin",
        "Agama", "Kewarganegaraan", "Status Perkawinan", "Pekerjaan", "Alamat"
    )
    val maxLabelWidth = labelList.maxOf { paint.measureText(it) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 5f
    val valueX = colonX + 10f
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText("Yang bertanda tangan di bawah ini:", labelX, y, paint)
    y += 30f

    fun drawLabelValue(label: String, value: String) {
        canvas.drawText(label, labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)

        val lines = wrapText(value, paint, maxTextWidth)
        for ((index, line) in lines.withIndex()) {
            val x = if (index == 0) valueX else valueX
            if (index != 0) y += 20f
            canvas.drawText(line, x, y, paint)
        }
        y += 20f
    }

    drawLabelValue("Nama", data.namaKetuaRtPengantar)
    drawLabelValue("Jabatan", data.jabatanKetuaRt)

    y += 10f
    canvas.drawText("Dengan ini menerangkan bahwa:", labelX, y, paint)
    y += 30f

    drawLabelValue("Nama", data.namaPengantar)
    drawLabelValue("TTL", "${data.tempatLahirPengantar}, ${data.tanggalLahirPengantar}")
    drawLabelValue("Jenis Kelamin", data.jenisKelaminPengantar)
    drawLabelValue("Kewarganegaraan", data.kewarganegaraan)
    drawLabelValue("Agama", data.agamaPengantar)
    drawLabelValue("Status Perkawinan", data.statusPerkawinanPengantar)
    drawLabelValue("Pekerjaan", data.pekerjaanPengantar)
    drawLabelValue("NIK", data.nikPengantar)

    // Alamat panjang
    canvas.drawText("Alamat", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    val alamatLines = wrapText(data.alamatPengantar, paint, maxTextWidth)
    for ((index, line) in alamatLines.withIndex()) {
        val x = valueX
        if (index != 0) y += 20f
        canvas.drawText(line, x, y, paint)
    }
    y += 45f

    // Tambahan keterangan seperti gambar
    paint.typeface = normal
    paint.textSize = 12f

    val keteranganA = "a.  Bahwa nama tersebut di atas adalah benar penduduk yang tinggal / berdomisili pada di ${data.rtPengantar} Kelurahan Talang Keramat."
    val keteranganB = "b.  Surat Keterangan ini diberikan untuk mengurus : ${data.keteranganTujuanPengantar}"

    val linesA = wrapText(keteranganA, paint, canvas.width - marginLeft * 2)
    for (line in linesA) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

    val linesB = wrapText(keteranganB, paint, canvas.width - marginLeft * 2)
    for (line in linesB) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }
    y += 35f

    val penutup = "Demikian Surat Pengantar ini dibuat dengan sebenarnya dan dapat dipergunakan sebagaimana mestinya."
    val linesPenutup = wrapText(penutup, paint, canvas.width - marginLeft * 2)
    for (line in linesPenutup) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }
    y += 20f

    // Tanggal dan tanda tangan
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"

    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = bold
    canvas.drawText("KETUA ${data.rtPengantar}", 380f, y, paint)
    y += 75f

    paint.typeface = bold
    canvas.drawText(data.namaKetuaRtPengantar, 380f, y, paint)

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Gagal menyimpan PDF: ${e.message}", Toast.LENGTH_LONG).show()
    } finally {
        pdfDocument.close()
    }
}



//DOMISILI
fun generatePdfDomisili(
    context: Context,
    data: DomisiliData,
    filename: String = "_Domisili.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT KETERANGAN DOMISILI"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint)
    y += 5f

    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor : 145 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 12f

    val labelList = listOf(
        "a. Nama", "b. NIP", "c. Jabatan",
        "a. Nama", "b. TTL", "c. Jenis Kelamin", "d. Agama",
        "e. Status", "f. Pekerjaan", "g. Tempat Tinggal"
    )
    val maxLabelWidth = labelList.maxOf { paint.measureText(it) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 5f
    val valueX = colonX + 8f // lebih kecil agar lebih padat rapi
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText("Yang bertanda tangan di bawah ini:", labelX, y, paint)
    y += 30f

// Data petugas
    canvas.drawText("a. Nama", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("LENI MARLINA SY,SE", valueX, y, paint); y += 20f

    canvas.drawText("b. NIP", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("19800626 201001 2011", valueX, y, paint); y += 20f

    canvas.drawText("c. Jabatan", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("Lurah Talang Keramat", valueX, y, paint); y += 30f

    canvas.drawText("Dengan ini menerangkan bahwa:", labelX, y, paint)
    y += 20f

    // Fungsi rapi
    fun drawField(label: String, value: String) {
        canvas.drawText(label, labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)
        val lines = wrapText(value, paint, maxTextWidth)
        for ((i, line) in lines.withIndex()) {
            if (i != 0) y += 20f
            canvas.drawText(line, valueX, y, paint)
        }
        y += 20f
    }

// Data warga
    drawField("a. Nama", data.nama)
    drawField("b. TTL", "${data.tempatLahir}, ${data.tanggalLahir}")
    drawField("c. Jenis Kelamin", data.jenisKelamin)
    drawField("d. Agama", data.agama)
    drawField("e. Status", data.statusPerkawinan)
    drawField("f. Pekerjaan", data.pekerjaan)
    drawField("g. Tempat Tinggal", data.alamat)

    y += 40f

    // Bagian naratif ketentuan
    paint.typeface = normal
    paint.textSize = 12f

    // Ganti bagian sebelumnya dengan ini untuk mencetak bagian keterangan RT dan keperluan
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    paint.textSize = 12f

    val kalimatPengantar = "Berdasarkan Surat Pengantar Dari Ketua ${data.rt} Nomor : 145/.../${data.rt}/KL-TK/2025"

// Cek apakah muat di satu baris
    val availableWidth = canvas.width - marginLeft * 2
    if (paint.measureText(kalimatPengantar) <= availableWidth) {
        canvas.drawText(kalimatPengantar, labelX, y, paint)
        y += 20f
    } else {
        // Jika tidak muat, pakai wrapText sebagai cadangan
        for (line in wrapText(kalimatPengantar, paint, availableWidth)) {
            canvas.drawText(line, labelX, y, paint)
            y += 45f
        }
    }


// 2. Baris tanggal dan isi kalimat utama
    val kalimatTanggal = "Tanggal ............................, bahwa memang benar Yang bersangkutan "
    canvas.drawText(kalimatTanggal, labelX, y, paint)

    val normalText1 = "bertempat tinggal/ berdomisili"

// Posisi X setelah kalimatTanggal
    val normalX1 = labelX + paint.measureText(kalimatTanggal)
    canvas.drawText(normalText1, normalX1, y, paint)


    y += 20f


// 3. Alamat domisili
    val alamat1 = "di ${data.alamat}, Kelurahan Talang Keramat Kecamatan Talang Kelapa"
    for (line in wrapText(alamat1, paint, canvas.width - marginLeft * 2)) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

// 4. Kabupaten
    canvas.drawText("Kabupaten Banyuasin.", labelX, y, paint)
    y += 30f

// 5. Keperluan
    val keperluan = "Surat Keterangan ini di keluarkan untuk keperluan : ${data.tujuanSurat}"
    for (line in wrapText(keperluan, paint, canvas.width - marginLeft * 2)) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

    // 6. Penutup
    val penutup = "Demikian Surat Keterangan ini dibuat untuk dipergunakan sebagaimana mestinya."
    for (line in wrapText(penutup, paint, canvas.width - marginLeft * 2)) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

    y += 20f
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint)
    y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint); y += 20f

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}


fun wrapText(text: String, paint: Paint, maxWidth: Float): List<String> {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""

    for (word in words) {
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        val lineWidth = paint.measureText(testLine)
        if (lineWidth <= maxWidth) {
            currentLine = testLine
        } else {
            lines.add(currentLine)
            currentLine = word
        }
    }

    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }

    return lines
}

//TIDAK MAMPU
fun generatePdfTidakMampu(
    context: Context,
    data: TidakMampuData,
    filename: String = "_TidakMampu.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT KETERANGAN TIDAK MAMPU"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint)
    y += 5f

    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor : 463 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 30f


    paint.typeface = bold
    paint.textSize = 12f

    val labelList = listOf(
        "a. Nama", "b. TTL", "c. Jenis Kelamin", "d. Warga Negara",
        "e. Agama", "f. Pekerjaan", "g. Tempat Tinggal"
    )
    val maxLabelWidth = labelList.maxOf { paint.measureText(it) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 6f
    val valueX = colonX + 6f
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText(
        "Yang bertanda tangan di bawah ini Pemerintah Kelurahan Talang Keramat Kecamatan Talang ",
        labelX, y, paint
    )
    y += 20f
    canvas.drawText(
        "Kelapa Kabupaten Banyuasin",
        labelX, y, paint
    )
    y += 20f
    canvas.drawText("Dengan ini menerangkan bahwa:", labelX, y, paint)
    y += 25f

    fun drawField(label: String, value: String) {
        canvas.drawText(label, labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)

        val lines = wrapText(value, paint, maxTextWidth)
        for ((i, line) in lines.withIndex()) {
            if (i != 0) y += 18f
            canvas.drawText(line, valueX, y, paint)
        }
        y += 22f
    }

// Data warga
    drawField("a. Nama", data.nama)
    drawField("b. TTL", "${data.tempatLahir}, ${data.tanggalLahir}")
    drawField("c. Jenis Kelamin", data.jenisKelamin)
    drawField("d. Warga Negara", data.kewarganegaraan)
    drawField("e. Agama", data.agama)
    drawField("f. Pekerjaan", data.pekerjaan)
    drawField("g. Tempat Tinggal", data.alamat)

    y += 25f

// Paragraf penjelas
    val rtRwText = "Berdasarkan Surat Pengantar RT Dari ${data.rt} RW ${data.rw}, benar nama tersebut di atas adalah warga yang berdomisili di RT ${data.rt} RW ${data.rw} Kelurahan Talang Keramat Kecamatan Talang Kelapa Kabupaten Banyuasin dan termasuk keluarga yang kurang mampu."
    for (line in wrapText(rtRwText, paint, canvas.width - marginLeft - marginRight)) {
        canvas.drawText(line, labelX, y, paint)
        y += 18f
    }

    y += 10f

    val tujuanText = "Surat Keterangan ini dikeluarkan untuk keperluan: ${data.tujuanSurat}"
    for (line in wrapText(tujuanText, paint, canvas.width - marginLeft - marginRight)) {
        canvas.drawText(line, labelX, y, paint)
        y += 25f
    }

    val longText1 = "Sehubungan dengan maksud tersebut di atas, diminta kepada pihak yang berwenang agar dapat memberikan bantuan seperlunya."
    for (line in wrapText(longText1, paint, canvas.width - marginLeft - marginRight)) {
        canvas.drawText(line, labelX, y, paint)
        y += 18f
    }

    y += 20f
    canvas.drawText(
        "Demikian Surat Keterangan ini dibuat dengan sebenarnya untuk dapat dipergunakan sebagaimana ",
        labelX, y, paint
    )
    y += 20f

    canvas.drawText(
        "mestinya.",
        labelX, y, paint
    )
    y += 30f

    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint)
    y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint); y += 20f

    pdfDocument.finishPage(page)

    val downloadsDir =
        android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}

//USAHA
fun generatePdfUsaha(
    context: Context,
    data: UsahaData,
    filename: String = "surat_usaha.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT KETERANGAN USAHA"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint)
    y += 5f

    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor : 145 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 30f


    paint.typeface = bold
    paint.textSize = 12f

    val labelList = listOf(
        "Nama", "TTL", "c. Jenis Kelamin", "NIK",
        "Alamat", "Jenis Usaha", "Ukuran Tempat Usaha", "Lokasi Usaha", "lama Usaha"
    )
    val maxLabelWidth = labelList.maxOf { paint.measureText(it) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 6f
    val valueX = colonX + 6f
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText(
        "Pemerintah Kelurahan Talang Keramat Kecamatan Talang Kecamatan Talang Kelapa Kabupaten  ",
        labelX, y, paint
    )
//    y += 20f
//    canvas.drawText(
//        "Kelapa Kabupaten Banyuasin",
//        labelX, y, paint
//    )
    y += 20f
    canvas.drawText("Banyuasin. Dengan ini menerangkan bahwa:", labelX, y, paint)
    y += 25f

    fun drawField(label: String, value: String) {
        canvas.drawText(label, labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)

        val lines = wrapText(value, paint, maxTextWidth)
        for ((i, line) in lines.withIndex()) {
            if (i != 0) y += 18f
            canvas.drawText(line, valueX, y, paint)
        }
        y += 22f
    }

// Data warga
    drawField("Nama", data.namaUsaha)
    drawField("TTL", "${data.tempatLahirUsaha}, ${data.tanggalLahirUsaha}")
    drawField("Jenis Kelamin", data.jenisKelaminUsaha)
    drawField("NIK", data.nikUsaha)
    drawField("Alamat", data.alamat)

    y += 25f

    val kalimatPengantar = "Berdasarkan Surat Pengantar Dari Ketua ${data.rtUsaha} ${data.rwUsaha}  Nomor : 145/.../RT.../KL-TK/2025"

// Cek apakah muat di satu baris
    val availableWidth = canvas.width - marginLeft * 2
    if (paint.measureText(kalimatPengantar) <= availableWidth) {
        canvas.drawText(kalimatPengantar, labelX, y, paint)
        y += 20f
    } else {
        // Jika tidak muat, pakai wrapText sebagai cadangan
        for (line in wrapText(kalimatPengantar, paint, availableWidth)) {
            canvas.drawText(line, labelX, y, paint)
            y += 45f
        }
    }

// 2. Baris tanggal dan isi kalimat utama
    val kalimatTanggal = "Tanggal ............................, Memang benar nama tersebut diatas memiliki usaha di Kelurahan "
    canvas.drawText(kalimatTanggal, labelX, y, paint)
    y += 20f

    canvas.drawText(
        "Talang Keramat kecamatan Talang Kelapa Kabupaten Banyuasin yaitu: ",
        labelX, y, paint
    )
    y += 40f

    drawField("Jenis Usaha", data.jenisUsaha )
    drawField("Ukuran Tempat Usaha", "${data.ukuranTempatUsaha} m²")
    drawField("Lokasi Usaha", data.lokasiUsaha)
    drawField("Lama Usaha",  "${data.lamaUsaha} tahun")

    y += 25f

    val tujuanText = "Surat Keterangan ini berlaku selama 3(Tiga) Bulan sejak dikeluarkan."
    for (line in wrapText(tujuanText, paint, canvas.width - marginLeft - marginRight)) {
        canvas.drawText(line, labelX, y, paint)
        y += 25f
    }

    y += 20f
    canvas.drawText(
        "Demikian Surat Keterangan Usaha ini dibuat dengan sebenarnya untuk dapat dipergunakan  ",
        labelX, y, paint
    )
    y += 20f

    canvas.drawText(
        "sebagaimana mestinya.",
        labelX, y, paint
    )
    y += 30f

    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint)
    y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint); y += 20f

    pdfDocument.finishPage(page)

    val downloadsDir =
        android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}


// PINDAH DATANG
fun generatePdfPindahDatang(
    context: Context,
    data: PindahDatangData,
    filename: String = "surat_pindah_datang.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f
    val title = "SURAT KETERANGAN PINDAH DATANG"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 10f
    val nomorText = "Nomor : 145 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 15f

    val labelX = marginLeft
    val colonX = labelX + 140f   // titik dua digeser ke kanan agar tidak terlalu dekat dengan label
    val valueX = colonX + 10f    // teks value tetap punya jarak rapi dari titik dua

    // Data Daerah Asal
    y += 10f
    canvas.drawText("A. DATA DAERAH ASAL", labelX, y, paint); y += 15f
    canvas.drawText("1. Nomor Kartu Keluarga", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.kkAsal}", valueX, y, paint); y += 15f
    canvas.drawText("2. Nama Kepala Keluarga", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.namaKepalaKeluargaAsal}", valueX, y, paint); y += 15f
    canvas.drawText("3. Alamat",  labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.alamatAsal}", valueX, y, paint); y += 15f
    canvas.drawText("a. Kode Pos", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.kodePosAsal}", valueX, y, paint); y += 15f
    canvas.drawText("b. RT", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rtAsal}", valueX, y, paint); y += 15f
    canvas.drawText("c. RW", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rwAsal}", valueX, y, paint); y += 15f
    canvas.drawText("4. Telepon", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.noTeleponAsal}", valueX, y, paint); y += 15f

    // Data Kepindahan
    y += 10f
    canvas.drawText("B. DATA KEPINDAHAN", labelX, y, paint); y += 15f
    canvas.drawText("1. Alasan Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.alasanPindah}", valueX, y, paint); y += 15f
    canvas.drawText("2. Alamat",  labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.alamatPindah}", valueX, y, paint); y += 15f
    canvas.drawText("a. Kode Pos", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.kodePosPindah}", valueX, y, paint); y += 15f
    canvas.drawText("b. RT", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rtPindah}", valueX, y, paint); y += 15f
    canvas.drawText("c. RW", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rwPindah}", valueX, y, paint); y += 15f
    canvas.drawText("3. Telepon",  labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.noTeleponPindah}", valueX, y, paint); y += 15f
    canvas.drawText("4. Klasifikasi Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.klasifikasiPindah}", valueX, y, paint); y += 15f
    canvas.drawText("5. Jenis Kepindahan", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.jenisKepindahan}", valueX, y, paint); y += 15f
    canvas.drawText("5. Status No. KK Tidak Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.statusNoKkTidakPindah}", valueX, y, paint); y += 15f
    canvas.drawText("6. Status No. KK Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.statusNoKkPindah}", valueX, y, paint); y += 15f
    canvas.drawText("7. Rencana Tanggal Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rencanaTanggalPindah}", valueX, y, paint); y += 15f
    canvas.drawText("8. NIK Keluarga yang Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.nikKeluargaYangDatangPindah}", valueX, y, paint); y += 15f
    canvas.drawText("9. Nama Keluarga yang Pindah", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.namaKeluargaYangDatangPindah}", valueX, y, paint); y += 15f

    // Data Daerah Tujuan
    y += 10f
    canvas.drawText("C. DATA DAERAH TUJUAN", labelX, y, paint); y += 15f
    canvas.drawText("1. Nomor Kartu Keluarga", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.noKkTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("2. Nama Kepala Keluarga", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.namaKepalaKeluargaTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("3. NIK Kepala Keluarga", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.nikKeluargaYangDatangTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("4. Status KK Pindah Tujuan", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.statusKkPindahTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("5. Tanggal Kedatangan", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.tanggalKedatanganTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("6. Alamat", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.alamatTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("a. RT", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rtTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("b. RW", labelX + 20f, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.rwTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("7. NIK Keluarga yang Datang", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.nikKeluargaYangDatangTujuan}", valueX, y, paint); y += 15f
    canvas.drawText("8. Nama Keluarga yang Datang", labelX, y, paint); canvas.drawText(":", colonX, y, paint); canvas.drawText("${data.namaKeluargaYangDatangTujuan}", valueX, y, paint); y += 15f
    y += 30f

    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint); y += 15f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint); y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint)

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}

//KETERANGAN
fun generatePdfKeterangan(
    context: Context,
    data: KeteranganData,
    filename: String = "surat_keterangan.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT KETERANGAN"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint)
    y += 5f

    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor : 145 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 12f

    val labelList = listOf(
        "a. Nama", "b. NIP", "c. Jabatan",
        "a. Nama", "b. TTL", "c. Jenis Kelamin", "d. Warga Negara",
        "e. Agama", "f. Pekerjaan", "g. Tempat Tinggal"
    )
    val maxLabelWidth = labelList.maxOf { paint.measureText(it) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 5f
    val valueX = colonX + 8f // lebih kecil agar lebih padat rapi
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText("Yang bertanda tangan di bawah ini:", labelX, y, paint)
    y += 30f

// Data petugas
    canvas.drawText("a. Nama", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("LENI MARLINA SY,SE", valueX, y, paint); y += 20f

    canvas.drawText("b. NIP", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("19800626 201001 2011", valueX, y, paint); y += 20f

    canvas.drawText("c. Jabatan", labelX, y, paint)
    canvas.drawText(":", colonX, y, paint)
    canvas.drawText("Lurah Talang Keramat", valueX, y, paint); y += 30f

    canvas.drawText("Dengan ini menerangkan bahwa:", labelX, y, paint)
    y += 20f

    // Fungsi rapi
    fun drawField(label: String, value: String) {
        canvas.drawText(label, labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)
        val lines = wrapText(value, paint, maxTextWidth)
        for ((i, line) in lines.withIndex()) {
            if (i != 0) y += 20f
            canvas.drawText(line, valueX, y, paint)
        }
        y += 20f
    }

// Data warga
    drawField("a. Nama", data.nama)
    drawField("b. TTL", "${data.tempatLahir}, ${data.tanggalLahir}")
    drawField("c. Jenis Kelamin", data.jenisKelamin)
    drawField("d. Warga Negara", data.wargaNegara)
    drawField("e. Agama", data.agama)
    drawField("f. Pekerjaan", data.pekerjaan)
    drawField("g. Tempat Tinggal", data.alamat)

    y += 40f

    // Bagian naratif ketentuan
    paint.typeface = normal
    paint.textSize = 12f

    // Ganti bagian sebelumnya dengan ini untuk mencetak bagian keterangan RT dan keperluan
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    paint.textSize = 12f

    val kalimatPengantar = "Berdasarkan Surat Pengantar Dari Ketua ${data.rt} ${data.rw}  Nomor : 145/.../RT.../KL-TK/2025"

// Cek apakah muat di satu baris
    val availableWidth = canvas.width - marginLeft * 2
    if (paint.measureText(kalimatPengantar) <= availableWidth) {
        canvas.drawText(kalimatPengantar, labelX, y, paint)
        y += 20f
    } else {
        // Jika tidak muat, pakai wrapText sebagai cadangan
        for (line in wrapText(kalimatPengantar, paint, availableWidth)) {
            canvas.drawText(line, labelX, y, paint)
            y += 45f
        }
    }


// 2. Baris tanggal dan isi kalimat utama
    val kalimatTanggal = "Tanggal ......................., bahwa memang benar Nama diatas berdomisili di ${data.rt} ${data.rw} Kelurahan"
    canvas.drawText(kalimatTanggal, labelX, y, paint)
    y += 20f

    canvas.drawText(
        "Talang Keramat Kecamatan Talang kelapa Kabupaten Banyuasin.", labelX, y,paint)
    y += 40f

// 5. Keperluan
    val keperluan = "Surat Keterangan ini di keluarkan untuk : ${data.tujuanSurat}"
    for (line in wrapText(keperluan, paint, canvas.width - marginLeft * 2)) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

    y += 25f

    // 6. Penutup
    val penutup = "Demikian Surat Keterangan ini dibuat dengan sebenarnya untuk dipergunakan sebagaimana mestinya."
    for (line in wrapText(penutup, paint, canvas.width - marginLeft * 2)) {
        canvas.drawText(line, labelX, y, paint)
        y += 20f
    }

    y += 20f
    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint)
    y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint); y += 20f

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}


//KEMATIAN
fun generatePdfKematian(
    context: Context,
    data: KematianData,
    filename: String = "surat_kematian.pdf"
) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(610, 935, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint()

    val bold = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    val normal = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)

    val marginLeft = 50f
    val marginTop = 50f
    var y = marginTop
    val marginRight = 40f

    val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_banyuasin)
    val scaledLogo = Bitmap.createScaledBitmap(logo, 120, 100, false)
    canvas.drawBitmap(scaledLogo, marginLeft, y, paint)

    y += 25f
    paint.typeface = bold
    paint.textSize = 16f

    val line1 = "PEMERINTAH KABUPATEN BANYUASIN"
    val line2 = "KECAMATAN TALANG KELAPA"
    val line3 = "KELURAHAN TALANG KERAMAT"
    val line4 = "Jalan Raya Talang Keramat No.01 RT.03 RW.01 (30761)"
    val line5 = "SUMATERA SELATAN"

    val centerX = { text: String -> (canvas.width - paint.measureText(text)) / 2 }

    canvas.drawText(line1, centerX(line1), y, paint); y += 20f
    canvas.drawText(line2, centerX(line2), y, paint); y += 20f
    canvas.drawText(line3, centerX(line3), y, paint); y += 20f

    paint.typeface = normal
    paint.textSize = 12f
    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
    canvas.drawText(line4, centerX(line4), y, paint); y += 20f

    paint.typeface = bold
    paint.textSize = 16f
    canvas.drawText(line5, centerX(line5), y, paint); y += 10f

    paint.strokeWidth = 2f
    canvas.drawLine(marginLeft, y, 595f - marginLeft, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 16f

    val title = "SURAT KETERANGAN KEMATIAN"
    val titleWidth = paint.measureText(title)
    val titleX = (canvas.width - titleWidth) / 2
    canvas.drawText(title, titleX, y, paint)
    y += 5f

    paint.strokeWidth = 1f
    canvas.drawLine(titleX, y, titleX + titleWidth, y, paint)
    y += 15

    paint.typeface = normal
    paint.textSize = 12f

    val nomorText = "Nomor : 474.3 / ... / KL - TK / 2025"
    val textWidth = paint.measureText(nomorText)
    canvas.drawText(nomorText, (canvas.width - textWidth) / 2, y, paint); y += 30f

    paint.typeface = bold
    paint.textSize = 12f

    val labelList = listOf(
        "Nama", "Jenis Kelamin", "Pekerjaan", "Alamat", "Umur",
        "Tanggal Meninggal", "Penyebab Kematian", "Yang Menerangkan",
        "Yang Melapor", "Hubungan Pelapor"
    )
    // Hitung lebar maksimal label
    val maxLabelWidth = labelList.maxOf { paint.measureText(it.trim()) }

    val labelX = marginLeft
    val colonX = labelX + maxLabelWidth + 6f // jarak titik dua agar rapi
    val valueX = colonX + 10f                // jarak nilai dari titik dua
    val maxTextWidth = canvas.width.toFloat() - valueX - marginRight

    paint.typeface = normal
    paint.textSize = 12f

    canvas.drawText(
        "Yang bertanda tangan di bawah ini Pemerintah Kelurahan Talang Keramat Kecamatan Talang",
        labelX, y, paint
    )
    y += 20f
    canvas.drawText("Kelapa Kabupaten Banyuasin, menerangkan bahwa:", labelX, y, paint)
    y += 30f

    // Fungsi menggambar dengan titik dua rapi
    fun drawField(label: String, value: String) {
        canvas.drawText(label.trim(), labelX, y, paint)
        canvas.drawText(":", colonX, y, paint)

        val lines = wrapText(value, paint, maxTextWidth)
        for ((i, line) in lines.withIndex()) {
            if (i != 0) y += 20f
            canvas.drawText(line, valueX, y, paint)
        }
        y += 20f
    }

    // Data jenazah & pelapor
    drawField("Nama", data.namaJenazah)
    drawField("Jenis Kelamin", data.jenisKelaminJenazah)
    drawField("Pekerjaan", data.pekerjaanJenazah)
    drawField("Alamat", data.alamatJenazah)
    drawField("Umur", "${data.umurJenazah} Tahun")
    drawField("Tanggal Meninggal", data.tanggalKematianJenazah)
    drawField("Penyebab Kematian", data.sebabKematianJenazah)
    drawField("Yang Menerangkan", data.namaYangMenerangkan)
    drawField("Yang Melapor", data.namaYangMelapor)
    drawField("Hubungan Pelapor", data.hubunganPelapor)
    y += 40f

    val calendar = Calendar.getInstance()
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("id", "ID"))
    val year = calendar.get(Calendar.YEAR)
    val formattedDate = "Talang Keramat, $day $month $year"
    canvas.drawText(formattedDate, 380f, y, paint)
    y += 20f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    canvas.drawText("LURAH TALANG KERAMAT", 380f, y, paint)
    y += 70f

    paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    canvas.drawText("LENI MARLINA SY,SE", 380f, y, paint); y += 20f
    canvas.drawText("NIP: 19800626 201001 2011", 380f, y, paint); y += 20f

    pdfDocument.finishPage(page)

    val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadsDir, filename)
    pdfDocument.writeTo(FileOutputStream(file))
    pdfDocument.close()

    Toast.makeText(context, "PDF berhasil disimpan di folder Downloads", Toast.LENGTH_LONG).show()
}



@Preview(showBackground = true)
@Composable
fun RiwayatScreenPreview() {
    RiwayatScreen(rememberNavController())
}
