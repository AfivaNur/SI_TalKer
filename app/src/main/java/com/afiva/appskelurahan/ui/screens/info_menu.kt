package com.afiva.appskelurahan.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.routing.Screen

// Data class untuk informasi lokasi
data class InfoLokasi(
    val nama: String,
    val alamat: String,
    val kodePos: String,
    val telepon: String,
    val jamOperasional: String,
    val luasWilayah: String,
    val jumlahPenduduk: String,
    val jumlahRT: String,
    val jumlahRW: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoKelurahanScreen (navController: NavController) { BackHandler(enabled = true) {}

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Info Lokasi", "Kontak")

    // Data informasi lokasi
    val infoLokasi = remember {
        InfoLokasi(
            nama = "Kelurahan Talang Keramat",
            alamat = "Jl. Raya Talang Keramat No.01 RT.03 RW.01, Kabupaten Banyuasin, Sumatera Selatan",
            kodePos = "30761",
            telepon = "0853-8408-1080",
            jamOperasional = "Senin - Jumat: 08.00 - 16.00 WIB",
            luasWilayah = "15.75 km²",
            jumlahPenduduk = "8.432 jiwa",
            jumlahRT = "30 RT",
            jumlahRW = "10 RW"
        )
    }
    val primaryColor = Color(0xFF00897B)

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        bottomBar = { CostomBottomNavigation(navController = navController, primaryColor = primaryColor) },
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
                                    text = "INFORMASI",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Kelurahan Talang Keramat",
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
        ) {
            HeaderInfoCard(infoLokasi)

            // LazyRow untuk tab scrolling samping
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tabs.size) { index ->
                    TabItem(
                        title = tabs[index],
                        isSelected = selectedTab == index,
                        onClick = { selectedTab = index }
                    )
                }
            }

            // Content berdasarkan tab yang dipilih
            when (selectedTab) {
                0 -> InfoLokasiContent(infoLokasi)
                1 -> KontakContent(infoLokasi)
            }
        }
    }
}

@Composable
fun TabItem(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        color = if (isSelected) Color(0xFF00897B) else Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color(0xFF00897B),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun HeaderInfoCard(infoLokasi: InfoLokasi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00897B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = infoLokasi.nama,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Banyuasin, Sumatera Selatan",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
//                    Icon(
//                        Icons.Default.AccountBox,
//                        contentDescription = null,
//                        tint = Color.White,
//                        modifier = Modifier.size(16.dp)
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = "${infoLokasi.jumlahPenduduk} penduduk",
//                        fontSize = 12.sp,
//                        color = Color.White.copy(alpha = 0.9f)
//                    )
                }
            }
        }
    }
}

@Composable
fun InfoLokasiContent(infoLokasi: InfoLokasi) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            InfoCard(
                title = "Informasi Dasar",
                icon = Icons.Default.Info,
                content = {
                    InfoRow("Nama Kelurahan", infoLokasi.nama)
                    InfoRow("Alamat", infoLokasi.alamat)
                    InfoRow("Kode Pos", infoLokasi.kodePos)
                    InfoRow("Luas Wilayah", infoLokasi.luasWilayah)
                }
            )
        }

//        item {
//            InfoCard(
//                title = "Data Kependudukan",
//                icon = Icons.Default.Person,
//                content = {
//                    InfoRow("Jumlah Penduduk", infoLokasi.jumlahPenduduk)
//                    InfoRow("Jumlah RT", infoLokasi.jumlahRT)
//                    InfoRow("Jumlah RW", infoLokasi.jumlahRW)
//                }
//            )
//        }

        item {
            InfoCard(
                title = "Jam Operasional",
                icon = Icons.Default.DateRange,
                content = {
                    Text(
                        text = infoLokasi.jamOperasional,
                        fontSize = 14.sp,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                }
            )
        }
    }
}

@Composable
fun KontakContent(infoLokasi: InfoLokasi) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                KontakCard(
                    icon = Icons.Default.Phone,
                    title = "Telepon",
                    value = "${infoLokasi.telepon} (Lurah)",
                    onClick = { /* Handle panggilan telepon */ }
                )
            }
        }
        item {
            KontakCard(
                icon = Icons.Default.LocationOn,
                title = "Alamat Lengkap",
                value = infoLokasi.alamat,
                onClick = { /* Handle buka maps */ }
            )
        }
    }
}

@Composable
fun InfoCard(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF00897B),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            content()
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun KontakCard(
    icon: ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0xFF00897B).copy(alpha = 0.1f),
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color(0xFF00897B),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun CostomBottomNavigation(
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
                    "Beranda",
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
                    "Riwayat",
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
                    "Info",
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
                    "Profil",
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
fun InfoKelurahanScreenPreview() {
    InfoKelurahanScreen(rememberNavController())
}