package com.piyush.voipsimulation.di

import androidx.room.Room
import com.piyush.voipsimulation.data.db.CallLogDatabase
import com.piyush.voipsimulation.data.repository.CallLogRepository
import com.piyush.voipsimulation.viewmodel.CallLogViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            CallLogDatabase::class.java,
            "call_log_db"
        ).build()
    }

    single { get<CallLogDatabase>().callLogDao() }

    single { CallLogRepository(get()) }

    viewModel { CallLogViewModel(get()) }
}
