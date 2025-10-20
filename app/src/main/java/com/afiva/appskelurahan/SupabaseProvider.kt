package com.afiva.appskelurahan

import android.app.Application
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseProvider {
    lateinit var client: SupabaseClient
        private set

    fun init(application: Application) {
        client = createSupabaseClient(
            supabaseUrl = "https://fsmfdjuhxrlsorpoayja.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZzbWZkanVoeHJsc29ycG9heWphIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDkwMTAyODQsImV4cCI6MjA2NDU4NjI4NH0.JlUWERSsCcmhr3DB5zCwEeBxmQOOdbf15_5UCB2FNSE"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}

