package com.piyush.voipsimulation

import android.app.Application
import com.piyush.voipsimulation.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class VOIPSimulationApp : Application() {
    override fun onCreate() {
        super.onCreate()
       startKoin {
            androidContext(this@VOIPSimulationApp)
            modules(appModule)
        }
    }
}
