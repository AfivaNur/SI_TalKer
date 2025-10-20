package com.afiva.appskelurahan.ui.screens

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.UserData
import com.afiva.appskelurahan.routing.Screen
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registerScreen(navController: NavController) {
    BackHandler(enabled = true) {}

    // State variables
    var noKK by remember { mutableStateOf("") }
    var nik by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var rt by remember { mutableStateOf("") }
    var rw by remember { mutableStateOf("") }
    var tempatLahir by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("") }
    var agama by remember { mutableStateOf("") }
    var statusPerkawinan by remember { mutableStateOf("") }
    var pekerjaan by remember { mutableStateOf("") }
    var kewarganegaraan by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    // Expanded states for dropdowns
    var expandedRT by remember { mutableStateOf(false) }
    var expandedRW by remember { mutableStateOf(false) }
    var expandedTempatLahir by remember { mutableStateOf(false) }
    var expandedAgama by remember { mutableStateOf(false) }
    var expandedStatusPerkawinan by remember { mutableStateOf(false) }
    var expandedPekerjaan by remember { mutableStateOf(false) }

    // Options
    val rtOptions = (1..30).map { "RT %02d".format(it) }
    val rwOptions = (1..10).map { "RW %02d".format(it) }
    val kotaOptions = listOf("Ambon", "Balikpapan", "Banda Aceh", "Bandar Lampung", "Bandung", "Banjar",
        "Banjarbaru", "Banjarmasin", "Batam", "Batu", "Baubau", "Bekasi", "Bengkulu",
        "Bima", "Binjai", "Bitung", "Blitar", "Bogor", "Bontang", "Bukittinggi",
        "Cilegon", "Cimahi", "Cirebon", "Denpasar", "Depok", "Dumai", "Gorontalo",
        "Gunungsitoli", "Jakarta", "Jambi", "Jayapura", "Kediri", "Kendari",
        "Kotamobagu", "Kupang", "Langsa", "Lhokseumawe", "Lubuklinggau", "Madiun",
        "Magelang", "Makassar", "Malang", "Manado", "Mataram", "Medan", "Metro",
        "Mojokerto", "Padang", "Padang Panjang", "Padang Sidempuan", "Pagar Alam",
        "Palangka Raya", "Palembang", "Palopo", "Palu", "Pangkalpinang", "Parepare",
        "Pariaman", "Pasuruan", "Payakumbuh", "Pekalongan", "Pekanbaru",
        "Pematangsiantar", "Pontianak", "Prabumulih", "Probolinggo", "Sabang",
        "Salatiga", "Samarinda", "Sawahlunto", "Semarang", "Serang", "Sibolga",
        "Singkawang", "Solok", "Sorong", "Subulussalam", "Sukabumi", "Sungai Penuh",
        "Surabaya", "Surakarta", "Tangerang", "Tangerang Selatan", "Tanjungbalai",
        "Tanjungpinang", "Tarakan", "Tasikmalaya", "Tebing Tinggi", "Tegal",
        "Ternate", "Tidore Kepulauan", "Tomohon", "Tual", "Yogyakarta")
    val agamaOptions = listOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
    val statusPerkawinanOptions = listOf("Belum Kawin", "Kawin", "Janda", "Duda")
    val pekerjaanOptions = listOf(
        "Buruh", "Dokter", "Dosen", "Guru", "Ibu Rumah Tangga", "Nelayan", "Notaris",
        "PNS", "Pegawai Swasta", "Pedagang", "Pelajar/Mahasiswa", "Pengacara", "Penulis",
        "Perawat", "Petani", "Polri", "Seniman", "Sopir", "TNI", "Tidak Bekerja", "Wiraswasta"
    )
    val jenisKelaminOptions = listOf("Laki-laki", "Perempuan")
    val kewarganegaraanOptions = listOf("WNI", "WNA")

    // DatePicker setup
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2000)
        set(Calendar.MONTH, 0)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
                tanggalLahir = formatter.format(selectedDate.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = Calendar.getInstance().timeInMillis
            val minDate = Calendar.getInstance().apply { set(1800, 0, 1) }
            datePicker.minDate = minDate.timeInMillis
        }
    }

    // Form validation
    val isNikValid = nik.length == 16 && nik.all { it.isDigit() }
    val isNoKKValid = noKK.length == 16 && noKK.all { it.isDigit() }
    val isFormValid = isNikValid &&
            isNoKKValid &&
            username.isNotBlank() &&
            password.isNotBlank() &&
            rt.isNotBlank() &&
            rw.isNotBlank() &&
            tempatLahir.isNotBlank() &&
            tanggalLahir.isNotBlank() &&
            jenisKelamin.isNotBlank() &&
            agama.isNotBlank() &&
            statusPerkawinan.isNotBlank() &&
            pekerjaan.isNotBlank() &&
            kewarganegaraan.isNotBlank() &&
            alamat.isNotBlank()

    val supabase = SupabaseProvider.client
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                Image(
                    painter = painterResource(id = R.drawable.logo_banyuasin),
                    contentDescription = "Logo Aplikasi",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(bottom = 8.dp)
                )
            }

            item {
                Text(
                    text = "REGISTRASI AKUN",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Silakan isi data berikut untuk membuat akun",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Informasi Keluarga",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        OutlinedTextField(
                            value = noKK,
                            onValueChange = {
                                if (it.length <= 16 && it.all(Char::isDigit)) noKK = it
                            },
                            label = { Text("Nomor Kartu Keluarga (KK)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = noKK.isNotEmpty() && noKK.length < 16,
                            singleLine = true
                        )
                        if (noKK.isNotEmpty() && noKK.length < 16) {
                            Text(
                                text = "Nomor KK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedTextField(
                            value = nik,
                            onValueChange = {
                                if (it.length <= 16 && it.all(Char::isDigit)) nik = it
                            },
                            label = { Text("NIK") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = nik.isNotEmpty() && nik.length < 16,
                            singleLine = true
                        )
                        if (nik.isNotEmpty() && nik.length < 16) {
                            Text(
                                text = "NIK harus terdiri dari 16 digit angka",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }

                        OutlinedTextField(
                            value = username,
                            onValueChange = {
                                username = it.uppercase().filter { c -> c.isLetter() || c.isWhitespace() }
                            },
                            label = { Text("Nama Lengkap") },
                            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Informasi Pribadi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        ExposedDropdownMenuBox(
                            expanded = expandedTempatLahir,
                            onExpandedChange = { expandedTempatLahir = !expandedTempatLahir }
                        ) {
                            OutlinedTextField(
                                value = tempatLahir,
                                onValueChange = {},
                                label = { Text("Tempat Lahir") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTempatLahir) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedTempatLahir,
                                onDismissRequest = { expandedTempatLahir = false }
                            ) {
                                kotaOptions.forEach { kota ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(kota) },
                                        onClick = {
                                            tempatLahir = kota
                                            expandedTempatLahir = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = tanggalLahir,
                            onValueChange = {},
                            label = { Text("Tanggal Lahir") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { datePickerDialog.show() }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Text(
                            text = "Jenis Kelamin",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            jenisKelaminOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = jenisKelamin == option,
                                        onClick = { jenisKelamin = option }
                                    )
                                    Text(option)
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedAgama,
                            onExpandedChange = { expandedAgama = !expandedAgama }
                        ) {
                            OutlinedTextField(
                                value = agama,
                                onValueChange = {},
                                label = { Text("Agama") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAgama) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedAgama,
                                onDismissRequest = { expandedAgama = false }
                            ) {
                                agamaOptions.forEach { agamaOption ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(agamaOption) },
                                        onClick = {
                                            agama = agamaOption
                                            expandedAgama = false
                                        }
                                    )
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedStatusPerkawinan,
                            onExpandedChange = { expandedStatusPerkawinan = !expandedStatusPerkawinan }
                        ) {
                            OutlinedTextField(
                                value = statusPerkawinan,
                                onValueChange = {},
                                label = { Text("Status Perkawinan") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatusPerkawinan) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedStatusPerkawinan,
                                onDismissRequest = { expandedStatusPerkawinan = false }
                            ) {
                                statusPerkawinanOptions.forEach { status ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(status) },
                                        onClick = {
                                            statusPerkawinan = status
                                            expandedStatusPerkawinan = false
                                        }
                                    )
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedPekerjaan,
                            onExpandedChange = { expandedPekerjaan = !expandedPekerjaan }
                        ) {
                            OutlinedTextField(
                                value = pekerjaan,
                                onValueChange = {},
                                label = { Text("Pekerjaan") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPekerjaan) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedPekerjaan,
                                onDismissRequest = { expandedPekerjaan = false }
                            ) {
                                pekerjaanOptions.forEach { pekerjaanOption ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(pekerjaanOption) },
                                        onClick = {
                                            pekerjaan = pekerjaanOption
                                            expandedPekerjaan = false
                                        }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Kewarganegaraan",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            kewarganegaraanOptions.forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = kewarganegaraan == option,
                                        onClick = { kewarganegaraan = option }
                                    )
                                    Text(option)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Alamat",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        ExposedDropdownMenuBox(
                            expanded = expandedRT,
                            onExpandedChange = { expandedRT = !expandedRT }
                        ) {
                            OutlinedTextField(
                                value = rt,
                                onValueChange = {},
                                label = { Text("RT") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRT) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedRT,
                                onDismissRequest = { expandedRT = false }
                            ) {
                                rtOptions.forEach { rtOption ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(rtOption) },
                                        onClick = {
                                            rt = rtOption
                                            expandedRT = false
                                        }
                                    )
                                }
                            }
                        }

                        ExposedDropdownMenuBox(
                            expanded = expandedRW,
                            onExpandedChange = { expandedRW = !expandedRW }
                        ) {
                            OutlinedTextField(
                                value = rw,
                                onValueChange = {},
                                label = { Text("RW") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRW) }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedRW,
                                onDismissRequest = { expandedRW = false }
                            ) {
                                rwOptions.forEach { rwOption ->
                                    androidx.compose.material3.DropdownMenuItem(
                                        text = { Text(rwOption) },
                                        onClick = {
                                            rw = rwOption
                                            expandedRW = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = alamat,
                            onValueChange = { alamat = it },
                            label = { Text("Alamat") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Informasi Akun
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Informasi Akun",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = {
                                if (it.length <= 8) {
                                    password = it
                                    passwordError = it.length < 8
                                }
                            },
                            label = { Text("Password") },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val iconRes = if (passwordVisible) R.drawable.visible else R.drawable.visibility
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            },
                            isError = passwordError,
                            supportingText = {
                                if (passwordError) {
                                    Text(
                                        text = "Password minimal 8 karakter untuk role non-admin",
                                        color = MaterialTheme.colorScheme.error,
                                        fontSize = 12.sp
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (!isFormValid) {
                                if (!isNoKKValid) passwordError = true
                                if (!isNikValid) passwordError = true
                                return@launch
                            }

                            val data = UserData(
                                no_kk = noKK,
                                nik = nik,
                                username = username,
                                password = password,
                                role = "Pengguna",
                                rt = rt,
                                rw = rw,
                                tempatLahir = tempatLahir,
                                tanggalLahir = tanggalLahir,
                                jenisKelamin = jenisKelamin,
                                agama = agama,
                                statusPerkawinan = statusPerkawinan,
                                pekerjaan = pekerjaan,
                                kewarganegaraan = kewarganegaraan,
                                alamat = alamat
                            )

                            try {
                                supabase.postgrest["UserData"].insert(
                                    mapOf(
                                        "no_kk" to data.no_kk,
                                        "nik" to data.nik,
                                        "username" to data.username,
                                        "password" to data.password,
                                        "role" to data.role,
                                        "rt" to data.rt,
                                        "rw" to data.rw,
                                        "tempat_lahir" to data.tempatLahir,
                                        "tanggal_lahir" to data.tanggalLahir,
                                        "jenis_kelamin" to data.jenisKelamin,
                                        "agama" to data.agama,
                                        "status_perkawinan" to data.statusPerkawinan,
                                        "pekerjaan" to data.pekerjaan,
                                        "kewarganegaraan" to data.kewarganegaraan,
                                        "alamat" to data.alamat
                                    )
                                )
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Register.route) { inclusive = true }
                                }
                            } catch (e: Exception) {
                                // Handle error (e.g., show a toast or snackbar)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00897B),
                        contentColor = Color.White
                    ),
                    enabled = isFormValid
                ) {
                    Text("DAFTAR")
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Sudah punya akun? ")
                    Text(
                        text = "Login disini",
                        color = Color(0xFF00897B),
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Login.route)
                        }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    registerScreen(navController = rememberNavController())
}