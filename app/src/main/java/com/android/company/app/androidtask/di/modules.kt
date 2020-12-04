package com.android.company.app.androidtask.di


import com.android.company.app.androidtask.data.remote.Network
import com.android.company.app.androidtask.data.remote.RemoteDataSource
import com.android.company.app.androidtask.repositories.IFoodRepo
import com.android.company.app.androidtask.repositories.FoodRepo
import com.android.company.app.androidtask.ui.foods.FoodsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private val network =  module {
    factory { Network.apiService }
}

private val remoteModule = module { factory { RemoteDataSource(get()) } }

private val repositoryModule = module { single<IFoodRepo> {FoodRepo(get())} }

private val viewModelModule = module {
    viewModel { FoodsViewModel(get()) }
}

fun getModules() : Array<Module>{

    return  arrayOf(
        network,
        remoteModule,
        repositoryModule,
        viewModelModule
    )
}