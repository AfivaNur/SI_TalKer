package com.afiva.appskelurahan.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.afiva.appskelurahan.SessionManager
import com.afiva.appskelurahan.SupabaseProvider
import com.afiva.appskelurahan.model.UserData
import com.afiva.appskelurahan.routing.Screen
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    BackHandler(enabled = true) {}
    var nik by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }
    var showEmailDialog by remember { mutableStateOf(false) }
    var emailInput by remember { mutableStateOf("") }
    var resetMessage by remember { mutableStateOf("") }
    var resetError by remember { mutableStateOf(false) }
    val passwordError = password.isNotEmpty() && password.length < 8
    val nikError = nik.isNotEmpty() && nik.length < 16

    val supabase = SupabaseProvider.client
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            if (loginError) {
                Text(
                    text = "Login gagal. NIK atau password salah!",
                    color = Color.Red,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (resetMessage.isNotEmpty()) {
                Text(
                    text = resetMessage,
                    color = if (resetError) Color.Red else Color.Green,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo_banyuasin),
                contentDescription = "Logo Aplikasi",
                modifier = Modifier.height(120.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "LOGIN",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Daftarkan akun dan login untuk mengakses aplikasi",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = nik,
                onValueChange = {
                    if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                        nik = it
                    }
                },
                label = { Text("NIK (16 digit)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                isError = nikError,
                supportingText = {
                    if (nikError) {
                        Text(
                            text = "NIK harus terdiri dari 16 digit angka untuk role non-admin",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 8) {
                        password = it
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val result = supabase
                            .postgrest["UserData"]
                            .select {
                                filter {
                                    eq("nik", nik)
                                    eq("password", password)
                                }
                            }.decodeList<UserData>()

                        if (result.isNotEmpty()) {
                            loginError = false
                            val user = result.first()
                            SessionManager.currentUser = user

                            when (user.role) {
                                "admin", "lurah" -> {
                                    navController.navigate(Screen.Beranda.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                                else -> {
                                    navController.navigate(Screen.InformasiScreen.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            }
                        } else {
                            loginError = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00897B),
                    contentColor = Color.White
                )
            ) {
                Text("LOGIN")
            }

            Spacer(modifier = Modifier.height(8.dp))


            // Lupa Password Text
            Text(
                text = "Lupa Password?",
                color = Color(0xFF00897B),
                modifier = Modifier
                    .clickable { navController.navigate("forgot_password") }
                    .padding(top = 8.dp)
            )

            Row {
                Text("Belum punya akun? ")
                Text(
                    text = "Daftar disini",
                    color = Color(0xFF00897B),
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Register.route)
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    // Dialog for entering email
    if (showEmailDialog) {
        AlertDialog(
            onDismissRequest = { showEmailDialog = false },
            title = { Text("Lupa Password") },
            text = {
                Column {
                    Text("Masukkan alamat email Anda untuk menerima link reset password.")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                // Step 1: Verify email exists in UserData table
                                val userData = supabase
                                    .postgrest["UserData"]
                                    .select {
                                        filter {
                                            eq("email", emailInput)
                                        }
                                    }.decodeList<UserData>()

                                if (userData.isNotEmpty()) {
                                    // Step 2: Trigger password reset email via Supabase Auth
                                    supabase.auth.resetPasswordForEmail(emailInput)
                                    resetMessage = "Link reset password telah dikirim ke $emailInput"
                                    resetError = false
                                    showEmailDialog = false
                                } else {
                                    resetMessage = "Email tidak ditemukan di database."
                                    resetError = true
                                }
                            } catch (e: Exception) {
                                resetMessage = "Gagal mengirim email reset: ${e.message}"
                                resetError = true
                            }
                        }
                    },
                    enabled = emailInput.isNotEmpty() && emailInput.contains("@")
                ) {
                    Text("Kirim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEmailDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}