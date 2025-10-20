package com.afiva.appskelurahan.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.afiva.appskelurahan.R
import com.afiva.appskelurahan.routing.Screen
import com.afiva.appskelurahan.v_model.ForgetPasswordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    navController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var nik by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    val viewModel = remember { ForgetPasswordViewModel() }

    val primaryColor = Color(0xFF00897B)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF8F9FA),
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Lupa Password",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = primaryColor,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nik,
                onValueChange = { nik = it.take(16).filter { char -> char.isDigit() } },
                label = { Text("NIK (16 digit)") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = {
                    newPassword = it.take(8)
                    passwordError = false
                },
                label = { Text("Password Baru (8 karakter)") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val iconRes = if (passwordVisible) R.drawable.visible else R.drawable.visibility
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = if (passwordVisible) "Sembunyikan" else "Tampilkan",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                isError = passwordError,
                supportingText = {
                    if (passwordError) Text(
                        "Password harus tepat 8 karakter",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it.take(8)
                    confirmPasswordError = false
                },
                label = { Text("Konfirmasi Password Baru") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val iconRes = if (confirmPasswordVisible) R.drawable.visible else R.drawable.visibility
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = if (confirmPasswordVisible) "Sembunyikan" else "Tampilkan",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                isError = confirmPasswordError,
                supportingText = {
                    if (confirmPasswordError) Text(
                        "Konfirmasi password harus sama dan tepat 8 karakter",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    var valid = true

                    if (nik.length != 16) {
                        valid = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("NIK harus 16 digit")
                        }
                    }

                    if (newPassword.length != 8) {
                        passwordError = true
                        valid = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Password harus tepat 8 karakter")
                        }
                    }

                    if (confirmPassword != newPassword || confirmPassword.length != 8) {
                        confirmPasswordError = true
                        valid = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Konfirmasi password tidak cocok atau kurang dari 8 karakter")
                        }
                    }

                    if (valid) {
                        isLoading = true
                        coroutineScope.launch {
                            val success = withContext(Dispatchers.IO) {
                                viewModel.updatePasswordByNik(nik, newPassword)
                            }

                            isLoading = false

                            if (success) {
                                snackbarHostState.showSnackbar("Password berhasil diubah.")
                                nik = ""
                                newPassword = ""
                                confirmPassword = ""
                                onNavigateBack()
                            } else {
                                snackbarHostState.showSnackbar("Gagal mengubah password.")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("KIRIM", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Login",
                color = primaryColor,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
            )
        }
    }
}

// ---------- ACTIVITY ENTRY POINT ----------
class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                ForgotPasswordScreen(
                    onNavigateBack = { finish() },
                    navController = navController
                )
            }
        }
    }
}
