package com.example.compradecartera.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.compradecartera.data.remote.ApiService
import com.example.compradecartera.data.remote.FinalizeTransactionDataSource
import com.example.compradecartera.data.remote.TransactionNumberDataSource
import com.example.compradecartera.data.remote.repository.FinalizeTransactionRepositoryImpl
import com.example.compradecartera.data.remote.repository.TransactionNumberRepositoryImpl
import com.example.compradecartera.domain.usecase.cardnumber.FinalizeTransactionUseCase
import com.example.compradecartera.domain.usecase.cardnumber.TransactionNumberUseCase
import com.example.compradecartera.presentation.ui.cardnumber.CardNumberViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ViewModelFactoryCardNumber : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CardNumberViewModel::class.java)) {
            val transactionNumberUseCase = TransactionNumberUseCase(
                TransactionNumberRepositoryImpl(
                    TransactionNumberDataSource(
                        apiService = Retrofit.Builder()
                            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService::class.java)
                    )
                )
            )
            val finalizeTransactionUseCase = FinalizeTransactionUseCase(
                FinalizeTransactionRepositoryImpl(
                    FinalizeTransactionDataSource(
                        apiService = Retrofit.Builder()
                            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/confirm/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(ApiService::class.java)
                    )
                )
            )
            return CardNumberViewModel(transactionNumberUseCase, finalizeTransactionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}