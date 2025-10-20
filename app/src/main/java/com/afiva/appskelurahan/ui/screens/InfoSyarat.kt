package com.afiva.appskelurahan.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllRequirementsScreen(
    navController: NavController,
    onAgreeClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    // Mencegah tombol back berfungsi (jika diinginkan)
    BackHandler(enabled = true) {

    }
    val allRequirements = listOf(
        "Surat Pengantar RT" to listOf(
            "Fotokopi KTP pemohon.",
            "Fotokopi Kartu Keluarga.",
            "Pas foto ukuran 3x4 sebanyak 2 lembar.",
            "Mengisi formulir permohonan."
        ),
        "Surat Domisili" to listOf(
            "Fotokopi KTP pemohon.",
            "Fotokopi Kartu Keluarga.",
            "Surat pengantar RT.",
            "Pas foto ukuran 3x4 sebanyak 2 lembar.",
            "Mengisi formulir permohonan."
        ),
        "Surat Keterangan Tidak Mampu" to listOf(
            "Fotokopi KTP dan KK.",
            "Surat pengantar RT.",
            "Surat keterangan sekolah (jika untuk pendidikan).",
            "Data pendukung penghasilan orang tua.",
            "Mengisi formulir permohonan SKTM."
        ),
        "Surat Keterangan Usaha" to listOf(
            "Fotokopi KTP dan KK.",
            "Surat pengantar RT.",
            "Surat keterangan domisili usaha (jika ada).",
            "Fotokopi bukti usaha (foto lokasi atau izin usaha).",
            "Formulir permohonan SK Usaha."
        ),
        "Surat Pindah" to listOf(
            "Fotokopi KTP dan KK.",
            "Surat pengantar RT.",
            "Alasan pindah dan tujuan domisili.",
            "Surat pernyataan bermaterai.",
            "Mengisi formulir permohonan pindah."
        ),
        "Surat Keterangan" to listOf(
            "Fotokopi KTP dan KK.",
            "Surat pengantar RT.",
            "Dokumen pendukung sesuai keperluan.",
            "Formulir permohonan.",
            "Keterangan tambahan jika dibutuhkan."
        ),
        "Surat Kematian" to listOf(
            "Fotokopi KTP almarhum.",
            "Fotokopi Kartu Keluarga.",
            "Surat keterangan kematian dari RS/dokter.",
            "Surat pengantar RT.",
            "Fotokopi KTP pelapor."
        ),
    )

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF00897B)),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBackClicked,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                "Persyaratan Layanan",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                "Kelurahan Talang Keramat",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    IconButton(
                        onClick = { /* Notifikasi */ },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.maskot_apk_kelurahan),
                            contentDescription = "Logout",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2F2F2))
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // Kartu info persyaratan
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "📋 Informasi Persyaratan",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFF00897B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Pastikan semua dokumen telah disiapkan sesuai dengan persyaratan yang tercantum untuk setiap jenis layanan.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Daftar layanan
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(allRequirements.size) { index ->
                        val (title, requirements) = allRequirements[index]

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            val current = expandedStates[title] ?: false
                                            expandedStates[title] = !current
                                        }
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color(0xFF00897B),
                                        modifier = Modifier.weight(1f)
                                    )

                                    Icon(
                                        imageVector = if (expandedStates[title] == true)
                                            Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        contentDescription = null,
                                        tint = Color(0xFF00897B)
                                    )
                                }

                                AnimatedVisibility(
                                    visible = expandedStates[title] == true,
                                    enter = expandVertically(),
                                    exit = shrinkVertically()
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(
                                                0xFFF8F9FA
                                            )
                                        )
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            requirements.forEachIndexed { reqIndex, item ->
                                                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(20.dp)
                                                            .background(
                                                                Color(0xFF00897B),
                                                                CircleShape
                                                            ),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            "${reqIndex + 1}",
                                                            fontSize = 10.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color.White
                                                        )
                                                    }
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(
                                                        item,
                                                        fontSize = 12.sp,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                if (reqIndex < requirements.lastIndex) {
                                                    Spacer(modifier = Modifier.height(4.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Spacer besar untuk jarak
                Spacer(modifier = Modifier.height(8.dp))

                // Tombol Lanjut
                Button(
                    onClick = onAgreeClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00897B)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Saya Mengerti & Lanjutkan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    )
}

    @Preview(showBackground = true)
@Composable
fun AllRequirementsScreenPreview() {
    AllRequirementsScreen( rememberNavController(),
        onAgreeClicked = {},
        onBackClicked = {}
    )
}