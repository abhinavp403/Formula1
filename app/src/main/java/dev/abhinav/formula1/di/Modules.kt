package dev.abhinav.formula1.di

import androidx.room.Room
import dev.abhinav.formula1.db.AppDatabase
import dev.abhinav.formula1.repository.CarRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration(false)
            .build()
    }

    single { get<AppDatabase>().carDao() }

    singleOf(::CarRepository).bind<CarRepository>()   // i.e single { CarRepository(get()) }
}