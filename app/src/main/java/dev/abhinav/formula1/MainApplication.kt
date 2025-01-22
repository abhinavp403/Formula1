package dev.abhinav.formula1

import android.app.Application
import dev.abhinav.formula1.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(sharedModule)
        }
    }
}