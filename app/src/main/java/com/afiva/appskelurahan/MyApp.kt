package com.afiva.appskelurahan


//import com.afiva.appskelurahan.data.di.appModules
import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inisialisasi Koin
        SupabaseProvider.init(this)
//        startKoin {
//            androidLogger(Level.ERROR)
//            androidContext(this@MyApplication)
//            modules(dataModule)
//        }
    }
}