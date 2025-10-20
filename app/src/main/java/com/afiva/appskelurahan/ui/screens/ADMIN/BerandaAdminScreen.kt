//package com.example.ui.BerandaAdmin
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.statusBars
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.windowInsetsPadding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.afiva.appskelurahan.R
//import com.afiva.appskelurahan.routing.Screen
//
//@Composable
//fun BerandaAdmin(navController: NavController) {
//    Scaffold(
//        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
//        bottomBar = { BottomNavigation(navController = navController) }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .background(Color(0xFFF2F2F2)).padding(innerPadding)
//        ) {
//            TopBar()
//            SearchBar()
//            MasterDataSection(navController)
//            InformationSection()
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
//}
//
//@Composable
//fun TopBar() {
//    Card(
//
//        modifier = Modifier
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFF00897B)),
//        shape = RoundedCornerShape(0.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column {
//                Text("SI TALKER", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
//                Text("Hai, Selamat Datang !", fontSize = 14.sp, color = Color.White)
//                Text("Di Layanan Kelurahan Talang Keramat", fontSize = 12.sp, color = Color.White)
//            }
//            IconButton(
//                onClick = { },
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(Color.White)
//            ) {
//                Icon(
//                    Icons.Filled.Notifications,
//                    contentDescription = "Notifications",
//                    tint = Color.Black
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun SearchBar() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//    ) {
//        OutlinedTextField(
//            value = "",
//            onValueChange = {},
//            placeholder = { Text("Cari", color = Color.Gray) },
//            trailingIcon = {
//                Icon(
//                    Icons.Filled.Search,
//                    contentDescription = "Search",
//                    tint = Color.Gray
//                )
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFFE6E6E6), RoundedCornerShape(24.dp)),
//            shape = RoundedCornerShape(24.dp)
//        )
//    }
//}
//
//@Composable
//fun MasterDataSection(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Master Data".toUpperCase(),
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        MasterDataRow(
//            items = listOf(
//                Triple("Permohonan", R.drawable.permohonan, Screen.FormPelaporKematian.route)
//            ),
//            navController = navController
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//}
//
//@Composable
//private fun MasterDataRow(
//    items: List<Triple<String, Int, String>>,
//    navController: NavController
//) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        items.forEach { (title, icon, route) ->
//            ServiceItem(
//                title = title.toUpperCase(),
//                icon = icon,
//                onClick = { navController.navigate(route) }
//            )
//        }
//
//        // Jika jumlah item kurang dari 3, tambahkan Spacer agar rata
//        repeat(3 - items.size) {
//            Spacer(modifier = Modifier.width(100.dp))
//        }
//    }
//}
//
//@Composable
//fun ServiceItem(
//    title: String,
//    icon: Int,
//    onClick: () -> Unit // tambahkan parameter fungsi
//) {
//    Card(
//        modifier = Modifier
//            .size(100.dp, 80.dp)
//            .padding(4.dp)
//            .clickable { onClick() }, // tambahkan ini
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF00897B))
//        ) {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(icon),
//                    contentDescription = "",
//                    tint = Color.Unspecified,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .padding(2.dp)
//                )
//                Text(
//                    title,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun ServiceItemMasterData(
//    title: String,
//    icon: Int,
//    onClick: () -> Unit // tambahkan parameter fungsi
//) {
//    Card(
//        modifier = Modifier
//            .size(100.dp, 80.dp)
//            .padding(4.dp)
//            .clickable { onClick() }, // tambahkan ini
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
//    ) {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF00897B))
//        ) {
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(icon),
//                    contentDescription = "",
//                    tint = Color.Unspecified,
//                    modifier = Modifier
//                        .size(40.dp)
//                        .padding(2.dp)
//                )
//                Text(
//                    title,
//                    fontSize = 12.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun InformationSection() {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        shape = RoundedCornerShape(8.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Info",
//                    tint = Color.Gray,
//                    modifier = Modifier.size(24.dp)
//                )
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                Text(
//                    "INFORMASI",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                "1. Pastikan semua dokumen telah di cek sebelum melakukan pengajuan pelayanan!",
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                "2. Pastikan sudah membuat SURAT PENGANTAR RT sesuai tujuan pelayanan terlebih dahulu!",
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
//        }
//    }
//}
//
//@Composable
//fun BottomNavigation(modifier: Modifier = Modifier, navController: NavController) {
//    val currentBackStackEntry = navController.currentBackStackEntryAsState()
//    val currentRoute = currentBackStackEntry.value?.destination?.route
//
//    NavigationBar {
//        NavigationBarItem(
//            selected = currentRoute == Screen.Beranda.route,
//            onClick = {
//                if (currentRoute != Screen.Beranda.route) {
//                    navController.navigate(Screen.Beranda.route) {
//                        popUpTo(Screen.Beranda.route) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                }
//            },
//            icon = {
//                Icon(
//                    painterResource(R.drawable.beranda_menu),
//                    contentDescription = null,
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Unspecified
//                )
//            },
//            label = { Text("Beranda") }
//        )
//        NavigationBarItem(
//            selected = currentRoute == Screen.RiwayatScreen.route,
//            onClick = {
//                if (currentRoute != Screen.RiwayatScreen.route) {
//                    navController.navigate(Screen.RiwayatScreen.route) {
//                        popUpTo(Screen.Beranda.route)
//                        launchSingleTop = true
//                    }
//                }
//            },
//            icon = {
//                Icon(
//                    painterResource(R.drawable.riwayat),
//                    contentDescription = null,
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Unspecified
//                )
//            },
//            label = { Text("Riwayat") }
//        )
//        NavigationBarItem(
//            selected = currentRoute == Screen.RiwayatScreen.route,
//            onClick = {
//                if (currentRoute != Screen.RiwayatScreen.route) {
//                    navController.navigate(Screen.RiwayatScreen.route) {
//                        popUpTo(Screen.Beranda.route)
//                        launchSingleTop = true
//                    }
//                }
//            },
//            icon = {
//                Icon(
//                    painterResource(R.drawable.tentang_kelurahan),
//                    contentDescription = null,
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Unspecified
//                )
//            },
//            label = { Text("Info") }
//        )
//        NavigationBarItem(
//            selected = currentRoute == Screen.ProfilScreen.route,
//            onClick = {
//                if (currentRoute != Screen.ProfilScreen.route) {
//                    navController.navigate(Screen.ProfilScreen.route) {
//                        popUpTo(Screen.Beranda.route)
//                        launchSingleTop = true
//                    }
//                }
//            },
//            icon = {
//                Icon(
//                    painterResource(R.drawable.profil_menu),
//                    contentDescription = null,
//                    modifier = Modifier.size(25.dp),
//                    tint = Color.Unspecified
//                )
//            },
//            label = { Text("Profil") }
//        )
//    }
//}
//
//
//@Preview
//@Composable
//fun BerandaAdminPreview(){
//    BerandaAdmin(rememberNavController())
//}