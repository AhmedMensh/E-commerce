package com.android.company.app.androidtask.ui

import android.app.Application
import com.android.company.app.androidtask.BuildConfig
import com.android.company.app.androidtask.data.remote.Network
import com.android.company.app.androidtask.di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Network.init("https://test/api/", BuildConfig.DEBUG)

        startKoin {
            androidContext(this@MyApplication)
            modules(*getModules())
        }
    }
}