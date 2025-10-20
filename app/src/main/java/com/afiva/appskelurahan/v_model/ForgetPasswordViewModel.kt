package com.afiva.appskelurahan.v_model

import com.afiva.appskelurahan.model.UpdatePasswordRequest
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ForgetPasswordViewModel {
    suspend fun updatePasswordByNik(nik: String, newPassword: String): Boolean {
        val supabaseUrl = "https://fsmfdjuhxrlsorpoayja.supabase.co"
        val table = "UserData"
        val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZzbWZkanVoeHJsc29ycG9heWphIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkwMTAyODQsImV4cCI6MjA2NDU4NjI4NH0.JlUWERSsCcmhr3DB5zCwEeBxmQOOdbf15_5UCB2FNSE"

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }

        return try {
            val response: HttpResponse = client.patch("$supabaseUrl/rest/v1/$table?nik=eq.$nik") {
                contentType(ContentType.Application.Json) // ✅ gunakan ini, bukan append string
                headers {
                    append(HttpHeaders.Authorization, "Bearer $supabaseKey")
                    append("apikey", supabaseKey)
                    append("Prefer", "return=minimal")
                }
                setBody(UpdatePasswordRequest(password = newPassword)) // ✅ field cocok dengan tabel Supabase
            }

            response.status == HttpStatusCode.NoContent || response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            client.close()
        }
    }
}
