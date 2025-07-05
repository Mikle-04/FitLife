package com.example.feature_auth.di

import com.example.feature_auth.data.local.TokenStorage
import com.example.feature_auth.data.remote.AuthApi
import com.example.feature_auth.data.repository.AuthRepositoryImpl
import com.example.feature_auth.data.repository.FakeAuthRepository
import com.example.feature_auth.domain.repository.AuthRepository
import com.example.feature_auth.presentation.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module{

    single {
        get<Retrofit>().create(AuthApi::class.java)
    }

    single { TokenStorage(ds = get()) }

//    single<AuthRepository> {
//        AuthRepositoryImpl(
//            api         = get(),
//            storage     = get(),
//            ioDispatcher= get(named("IO"))
//        )
//    }
    //Фейковый репозиторий
    single<AuthRepository> {
        FakeAuthRepository()
    }

    viewModel {
        AuthViewModel(
            repository     = get(),
            mainDispatcher = get(named("Main"))
        )
    }
}