package com.example.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.DomisiliData
import com.afiva.appskelurahan.model.KematianData
import com.afiva.appskelurahan.model.KeteranganData
import com.afiva.appskelurahan.model.PindahDatangData
import com.afiva.appskelurahan.model.SuratPengantarRTData
import com.afiva.appskelurahan.model.TidakMampuData
import com.afiva.appskelurahan.model.UsahaData
import com.afiva.appskelurahan.model.UserData
import com.afiva.appskelurahan.routing.Screen
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    BackHandler(enabled = true) {}
    val primaryColor = Color(0xFF00897B)
    val lightTeal = Color(0xFFE0F2F1)
    val surfaceColor = Color(0xFFF8F9FA)

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        bottomBar = { BottomNavigation(navController = navController, primaryColor = primaryColor) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(surfaceColor)
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(primaryColor)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                TopBar(
                    primaryColor = primaryColor,
                    onLogout = {
                        navController.navigate("login") {
                            popUpTo("beranda") { inclusive = true }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            InfoSyaratCard(navController, primaryColor)
            PopulationCard(primaryColor)

            val isAdminOrLurah = SessionManager.currentUser?.role in listOf("admin", "lurah")
            if (isAdminOrLurah) {
                RiwayatStatistikDiagram(primaryColor) // Show diagram for admin/lurah
            } else {
                SuratPengantarRTCard(navController, primaryColor)
                ServicesSection(navController, primaryColor)
            }

            InformationSection(primaryColor)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TopBar(primaryColor: Color, onLogout: () -> Unit) {
    val role = SessionManager.currentUser?.role
    val isAdminOrLurah = role in listOf("admin", "lurah")
    val welcomeMessage = when (role) {
        "admin" -> "Halo Admin, Selamat Datang!"
        "lurah" -> "Halo Lurah, Selamat Datang!"
        else -> "Hai, Selamat Datang!"
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.size(60.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_banyuasin),
                        contentDescription = null,
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

            Column {
                Text(
                    "SI TALKER",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (isAdminOrLurah) Color(0xFFFFC107).copy(alpha = 0.4f) else Color.Transparent
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        welcomeMessage,
                        fontSize = if (isAdminOrLurah) 16.sp else 14.sp,
                        fontWeight = if (isAdminOrLurah) FontWeight.ExtraBold else FontWeight.Normal,
                        color = if (isAdminOrLurah) Color.White else Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Start
                    )
                }

                Text(
                    "Di Layanan Kelurahan Talang Keramat",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        IconButton(
            onClick = onLogout,
            modifier = Modifier.size(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun PopulationCard(primaryColor: Color) {
    val role = SessionManager.currentUser?.role
    val isAdminOrLurah = role in listOf("admin", "lurah")
    val backgroundColor = if (isAdminOrLurah) Color(0xFFFF7f9d3b) else primaryColor
    val textColor = Color.White

    // State to hold male and female counts
    var maleCount by remember { mutableStateOf(0) }
    var femaleCount by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    // Fetch counts when the composable is first displayed
    LaunchedEffect(Unit) {
        scope.launch {
            val (males, females) = getUserCountsByGender()
            maleCount = males
            femaleCount = females
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "PENDUDUK TALANG KERAMAT YANG MENDAFTAR",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                PopulationStat("Total Penduduk", "${maleCount + femaleCount}", textColor)

                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(1.dp)
                        .background(textColor.copy(alpha = 0.5f))
                )

                PopulationStat("Laki-laki", "$maleCount", textColor, true)

                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(1.dp)
                        .background(textColor.copy(alpha = 0.5f))
                )

                PopulationStat("Perempuan", "$femaleCount", textColor, true)
            }
        }
    }

    if (!isAdminOrLurah) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Wajib ada SURAT PENGANTAR RT!",
                fontSize = 15.sp,
                color = Color.Red
            )
        }
    }
}

@Composable
fun RiwayatStatistikDiagram(primaryColor: Color) {
    val scope = rememberCoroutineScope()

    var dataMap by remember {
        mutableStateOf<Map<String, Int>>(emptyMap())
    }

    LaunchedEffect(Unit) {
        scope.launch {
            val domisili = getCountFromTable("DomisiliData")
            val usaha = getCountFromTable("UsahaData")
            val tidakMampu = getCountFromTable("TidakMampuData")
            val kematian = getCountFromTable("KematianData")
            val keterangan = getCountFromTable("KeteranganData")
            val pindah = getCountFromTable("PindahDatangData")
            val pengantar = getCountFromTable("SuratPengantarRTData")

            dataMap = mapOf(
                "Domisili" to domisili,
                "Usaha" to usaha,
                "Tidak Mampu" to tidakMampu,
                "Kematian" to kematian,
                "Keterangan" to keterangan,
                "Pindah" to pindah,
                "Pengantar RT" to pengantar,
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Diagram Pengajuan Pelayanan",
                style = MaterialTheme.typography.titleMedium,
                color = primaryColor
            )
            Spacer(modifier = Modifier.height(10.dp))

            if (dataMap.isNotEmpty()) {
                val total = dataMap.values.sum()
                val pieData = dataMap.entries.mapIndexed { index, entry ->
                    PieSliceData(
                        label = "${entry.key}: ${entry.value}",
                        value = entry.value.toFloat(),
                        color = chartColors[index % chartColors.size]
                    )
                }

                CustomPieChart(
                    pieData = pieData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "Total Pengajuan: $total",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))
                Column {
                    pieData.forEach { slice ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(slice.color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = slice.label,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2C3E50)
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primaryColor)
                }
            }
        }
    }
}

data class PieSliceData(
    val label: String,
    val value: Float,
    val color: Color
)

@Composable
fun CustomPieChart(
    pieData: List<PieSliceData>,
    modifier: Modifier = Modifier
) {
    val totalValue = pieData.sumOf { it.value.toDouble() }.toFloat()
    if (totalValue == 0f) return

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = minOf(canvasWidth, canvasHeight) / 2 * 0.6f
        val center = Offset(canvasWidth / 2, canvasHeight / 2)

        var startAngle = 0f

        pieData.forEach { slice ->
            val sweepAngle = (slice.value / totalValue) * 360f
            drawArc(
                color = slice.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            startAngle += sweepAngle
        }

        drawCircle(
            color = Color.White,
            radius = radius * 0.4f,
            center = center
        )
    }
}

private val chartColors = listOf(
    Color(0xFF00897B),
    Color(0xFF00ACC1),
    Color(0xFFEF5350),
    Color(0xFFFFA726),
    Color(0xFF7E57C2),
    Color(0xFF66BB6A),
    Color(0xFF5C6BC0)
)

suspend fun getCountFromTable(table: String): Int = try {
    withContext(Dispatchers.IO) {
        val result = SupabaseProvider.client.from(table).select()
        when (table) {
            "SuratPengantarRTData" -> result.decodeList<SuratPengantarRTData>().size
            "DomisiliData" -> result.decodeList<DomisiliData>().size
            "UsahaData" -> result.decodeList<UsahaData>().size
            "TidakMampuData" -> result.decodeList<TidakMampuData>().size
            "KematianData" -> result.decodeList<KematianData>().size
            "KeteranganData" -> result.decodeList<KeteranganData>().size
            "PindahDatangData" -> result.decodeList<PindahDatangData>().size
            else -> 0
        }
    }
} catch (e: Exception) {
    Log.e("Supabase", "Error fetching table $table: ${e.message}")
    e.printStackTrace()
    0
}

suspend fun getUserCountsByGender(): Pair<Int, Int> = withContext(Dispatchers.IO) {
    try {
        val users = SupabaseProvider.client.from("UserData").select {
            filter { eq("role", "Pengguna") }
        }.decodeList<UserData>()

        val maleCount = users.count { it.jenisKelamin == "Laki-laki" }
        val femaleCount = users.count { it.jenisKelamin == "Perempuan" }
        maleCount to femaleCount
    } catch (e: Exception) {
        Log.e("Supabase", "Error fetching UserData: ${e.message}")
        e.printStackTrace()
        0 to 0
    }
}

@Composable
fun PopulationStat(label: String, value: String, textColor: Color, showIcon: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )

        if (showIcon) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = label,
                    modifier = Modifier.size(16.dp),
                    tint = textColor.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Text(
                value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SuratPengantarRTCard(navController: NavController, primaryColor: Color) {
    val isAdminOrLurah = SessionManager.currentUser?.role in listOf("admin", "lurah")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Text(
            text = "Buat disini jika belum ada.",
            fontSize = 12.sp,
            color = primaryColor
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                if (!isAdminOrLurah) {
                    navController.navigate(Screen.SuratPengantar.route)
                }
            }
            .shadow(2.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.writing),
                    contentDescription = "Logo Surat Pengantar",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Surat Pengantar RT",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f))
                    .clickable {
                        if (!isAdminOrLurah) {
                            navController.navigate(Screen.SuratPengantar.route)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Go to Surat Pengantar RT",
                    tint = primaryColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun InfoSyaratCard(navController: NavController, primaryColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                navController.navigate(Screen.AllRequirementsScreen.route)
            }
            .shadow(2.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Go to Info Syarat",
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Info Syarat Pelayanan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(primaryColor.copy(alpha = 0.1f))
                    .clickable {
                        navController.navigate(Screen.AllRequirementsScreen.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Go to Info Syarat",
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ServicesSection(navController: NavController, primaryColor: Color) {
    val isAdminOrLurah = SessionManager.currentUser?.role in listOf("admin", "lurah")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "PELAYANAN",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF2C3E50)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ServiceItem(
                title = "DOMISILI",
                icon = R.drawable.domisili,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.SuratKeteranganDomisili.route) } },
                primaryColor = primaryColor
            )
            ServiceItem(
                title = "TIDAK MAMPU",
                icon = R.drawable.tidak_mampu,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.SuratKeteranganTidakMampu.route) } },
                primaryColor = primaryColor
            )
            ServiceItem(
                title = "USAHA",
                icon = R.drawable.usaha,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.SuratKeteranganUsaha.route) } },
                primaryColor = primaryColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ServiceItem(
                title = "PINDAH",
                icon = R.drawable.pindah_datang,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.DataDaerahAsal.route) } },
                primaryColor = primaryColor
            )
            ServiceItem(
                title = "KETERANGAN",
                icon = R.drawable.keterangan,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.SuratKeterangan.route) } },
                primaryColor = primaryColor
            )
            ServiceItem(
                title = "KEMATIAN",
                icon = R.drawable.kematian,
                onClick = if (isAdminOrLurah) { {} } else { { navController.navigate(Screen.FormJenazah.route) } },
                primaryColor = primaryColor
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ServiceItem(
    title: String,
    icon: Int,
    onClick: () -> Unit,
    primaryColor: Color
) {
    Card(
        modifier = Modifier
            .size(100.dp, 100.dp)
            .padding(4.dp)
            .clickable { onClick() }
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF2C3E50),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun InformationSection(primaryColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(primaryColor.copy(alpha = 0.1f), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = primaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "INFORMASI",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF2C3E50)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "1. Pastikan semua dokumen telah di cek sebelum melakukan pengajuan pelayanan!",
                fontSize = 12.sp,
                color = Color(0xFF5D6D7E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "2. Pastikan sudah membuat SURAT PENGANTAR RT sesuai tujuan pelayanan terlebih dahulu!",
                fontSize = 12.sp,
                color = Color(0xFF5D6D7E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "3. Berkas asli dan berkas pelayanan yang telah dibuat harap dibawa ke Kelurahan!",
                fontSize = 12.sp,
                color = Color(0xFF5D6D7E)
            )
        }
    }
}

@Composable
fun BottomNavigation(
    navController: NavController,
    primaryColor: Color
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Beranda.route,
            onClick = {
                if (currentRoute != Screen.Beranda.route) {
                    navController.navigate(Screen.Beranda.route) {
                        popUpTo(Screen.Beranda.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.beranda_menu),
                    contentDescription = "Beranda",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            },
            label = {
                Text(
                    text = "Beranda",
                    color = if (currentRoute == Screen.Beranda.route) primaryColor else Color(0xFF9E9E9E)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = primaryColor.copy(alpha = 0.1f),
                selectedTextColor = primaryColor,
                unselectedTextColor = Color(0xFF9E9E9E)
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.RiwayatScreen.route,
            onClick = {
                if (currentRoute != Screen.RiwayatScreen.route) {
                    navController.navigate(Screen.RiwayatScreen.route) {
                        popUpTo(Screen.Beranda.route)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.riwayat),
                    contentDescription = "Riwayat",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            },
            label = {
                Text(
                    text = "Riwayat",
                    color = if (currentRoute == Screen.RiwayatScreen.route) primaryColor else Color(0xFF9E9E9E)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = primaryColor.copy(alpha = 0.1f),
                selectedTextColor = primaryColor,
                unselectedTextColor = Color(0xFF9E9E9E)
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.InfoKelurahanScreen.route,
            onClick = {
                if (currentRoute != Screen.InfoKelurahanScreen.route) {
                    navController.navigate(Screen.InfoKelurahanScreen.route) {
                        popUpTo(Screen.Beranda.route)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.tentang_kelurahan),
                    contentDescription = "Info",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            },
            label = {
                Text(
                    text = "Info",
                    color = if (currentRoute == Screen.InfoKelurahanScreen.route) primaryColor else Color(0xFF9E9E9E)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = primaryColor.copy(alpha = 0.1f),
                selectedTextColor = primaryColor,
                unselectedTextColor = Color(0xFF9E9E9E)
            )
        )

        NavigationBarItem(
            selected = currentRoute == Screen.ProfilScreen.route,
            onClick = {
                if (currentRoute != Screen.ProfilScreen.route) {
                    navController.navigate(Screen.ProfilScreen.route) {
                        popUpTo(Screen.Beranda.route)
                        launchSingleTop = true
                    }
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.profil_menu),
                    contentDescription = "Profil",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            },
            label = {
                Text(
                    text = "Profil",
                    color = if (currentRoute == Screen.ProfilScreen.route) primaryColor else Color(0xFF9E9E9E)
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = primaryColor.copy(alpha = 0.1f),
                selectedTextColor = primaryColor,
                unselectedTextColor = Color(0xFF9E9E9E)
            )
        )
    }
}

@Preview
@Composable
fun BerandaPreview() {
    MainScreen(rememberNavController())
}