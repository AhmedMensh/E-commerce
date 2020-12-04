package com.android.company.app.androidtask

import android.app.Application
import android.content.Context
import com.android.company.app.androidtask.data.remote.Network
import com.android.company.app.androidtask.di.getModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Network.init(BuildConfig.BASE_URL, BuildConfig.DEBUG)

        startKoin {
            androidContext(this@MyApplication)
            modules(*getModules())
        }
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}