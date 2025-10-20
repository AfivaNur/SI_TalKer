package com.afiva.appskelurahan.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.model.UserData
import com.afiva.appskelurahan.routing.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    BackHandler(enabled = true) {}
    val primaryColor = Color(0xFF00897B)

    // Mengambil data dari SessionManager, dengan nilai default jika kosong
    val currentUser = SessionManager.currentUser ?: UserData()
    var username by remember { mutableStateOf(currentUser.username.ifEmpty { "Pengguna Baru" }) }
    val nik = currentUser.nik.ifEmpty { "Belum Terdaftar" }
    val no_kk = currentUser.no_kk.ifEmpty { "Belum Terdaftar" }
    var role by remember { mutableStateOf(currentUser.role.ifEmpty { "Pengguna" }) }

    // Edit dialog states
    var showEditDialog by remember { mutableStateOf(false) }
    var editingField by remember { mutableStateOf("") }
    var editingValue by remember { mutableStateOf("") }
    var editingTitle by remember { mutableStateOf("") }

    fun resetForm() {
        username = ""
        role = ""
        showEditDialog = false
        editingField = ""
        editingValue = ""
        editingTitle = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Profil",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF009688)
                )
            )
        },
        bottomBar = {
            ProfilBottomNavigation(navController = navController, primaryColor = primaryColor)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            // Purple header section with profile
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF009688))
                    .padding(
                        bottom = 24.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier
                            .size(145.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Card(
                            modifier = Modifier.size(145.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f))
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.maskot_apk_kelurahan),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Username (non-editable directly)
                    Text(
                        text = username,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Role Indicator
                    Text(
                        text = "Role: ${role}",
                        fontSize = 12.sp,
                        color = if (role == "admin") Color(0xFFFFEB3B) else Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            // White content section
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // NIK Section
                ContactSection(
                    title = "NIK",
                    items = listOf(
                        ContactItem("NIK", nik, Icons.Default.Person)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // No KK Section
                ContactSection(
                    title = "No. Kartu Keluarga",
                    items = listOf(
                        ContactItem("No KK", no_kk, Icons.Default.Person)
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Bottom Action Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            editingField = "username"
                            editingValue = username
                            editingTitle = "Edit Username"
                            showEditDialog = true
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit")
                    }
                }
            }
        }
    }

    // Edit Dialog
    if (showEditDialog) {
        EditDialog(
            title = editingTitle,
            value = editingValue,
            onValueChange = { editingValue = it },
            onDismiss = { showEditDialog = false },
            onConfirm = {
                when (editingField) {
                    "username" -> username = editingValue
                    "role" -> role = editingValue
                }
                showEditDialog = false
            },
            keyboardType = KeyboardType.Text
        )
    }
}

@Composable
fun ContactSection(
    title: String,
    items: List<ContactItem>
) {
    Column {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        items.forEach { item ->
            ContactItemRow(item = item)
            if (item != items.last()) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun ContactItemRow(
    item: ContactItem
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                if (item.label.isNotEmpty()) {
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                }
                Text(
                    text = item.value,
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun EditDialog(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val focusManager = LocalFocusManager.current
    val isValidInput = value.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title, fontWeight = FontWeight.Medium) },
        text = {
            Column {
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus(); if (isValidInput) onConfirm() }),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    isError = !isValidInput && value.isNotEmpty()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = isValidInput) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Cancel")
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}

data class ContactItem(
    val label: String,
    val value: String,
    val icon: ImageVector
)

@Composable
fun ProfilBottomNavigation(
    navController: NavController,
    primaryColor: Color
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Beranda.route,
            onClick = { if (currentRoute != Screen.Beranda.route) navController.navigate(Screen.Beranda.route) { popUpTo(Screen.Beranda.route) { inclusive = true }; launchSingleTop = true } },
            icon = { Icon(painter = painterResource(R.drawable.beranda_menu), contentDescription = "Beranda", modifier = Modifier.size(24.dp), tint = Color.Unspecified) },
            label = { Text("Beranda") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = Color(0xFF9E9E9E),
                unselectedTextColor = Color(0xFF9E9E9E),
                indicatorColor = primaryColor.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.RiwayatScreen.route,
            onClick = { if (currentRoute != Screen.RiwayatScreen.route) navController.navigate(Screen.RiwayatScreen.route) { popUpTo(Screen.Beranda.route); launchSingleTop = true } },
            icon = { Icon(painter = painterResource(R.drawable.riwayat), contentDescription = "Riwayat", modifier = Modifier.size(24.dp), tint = Color.Unspecified) },
            label = { Text("Riwayat") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = Color(0xFF9E9E9E),
                unselectedTextColor = Color(0xFF9E9E9E),
                indicatorColor = primaryColor.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.InfoKelurahanScreen.route,
            onClick = { if (currentRoute != Screen.InfoKelurahanScreen.route) navController.navigate(Screen.InfoKelurahanScreen.route) { popUpTo(Screen.Beranda.route); launchSingleTop = true } },
            icon = { Icon(painter = painterResource(R.drawable.tentang_kelurahan), contentDescription = "Info", modifier = Modifier.size(24.dp), tint = Color.Unspecified) },
            label = { Text("Info") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = Color(0xFF9E9E9E),
                unselectedTextColor = Color(0xFF9E9E9E),
                indicatorColor = primaryColor.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.ProfilScreen.route,
            onClick = { if (currentRoute != Screen.ProfilScreen.route) navController.navigate(Screen.ProfilScreen.route) { popUpTo(Screen.Beranda.route); launchSingleTop = true } },
            icon = { Icon(painter = painterResource(R.drawable.profil_menu), contentDescription = "Profil", modifier = Modifier.size(24.dp), tint = Color.Unspecified) },
            label = { Text("Profil") },
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(rememberNavController())
    }
}