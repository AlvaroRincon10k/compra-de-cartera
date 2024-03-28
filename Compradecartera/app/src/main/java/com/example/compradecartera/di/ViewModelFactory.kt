package com.example.compradecartera.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.compradecartera.data.remote.ApiService
import com.example.compradecartera.data.remote.UserDataSource
import com.example.compradecartera.data.remote.repository.UserRepositoryImpl
import com.example.compradecartera.domain.usecase.UserUseCase
import com.example.compradecartera.presentation.ui.dashboard.DashBoardViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DashBoardViewModel(
            UserUseCase(
                UserRepositoryImpl(
                    UserDataSource(
                       apiService =  Retrofit.Builder()
                            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/user/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService::class.java)
                    )
                )
            )
        ) as T
    }
}