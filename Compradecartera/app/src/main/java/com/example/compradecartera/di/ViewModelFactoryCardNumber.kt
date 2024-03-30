package com.example.compradecartera.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.compradecartera.data.remote.ApiService
import com.example.compradecartera.data.remote.BinCheckerDataSource
import com.example.compradecartera.data.remote.FinalizeTransactionDataSource
import com.example.compradecartera.data.remote.TransactionNumberDataSource
import com.example.compradecartera.data.remote.repository.BinCheckerRepositoryImpl
import com.example.compradecartera.domain.usecase.cardnumber.BinCheckerUseCase
import com.example.compradecartera.domain.usecase.cardnumber.FinalizeTransactionUseCase
import com.example.compradecartera.presentation.ui.cardnumber.CardNumberViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL_TRANSACTION = "https://api.mocki.io/"
const val BASE_URL_BIN_CHECKER = "https://bin-ip-checker.p.rapidapi.com/"

class ViewModelFactoryCardNumber : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(CardNumberViewModel::class.java)) {
            val repositoryImplBinChecker = BinCheckerRepositoryImpl(
                BinCheckerDataSource(
                    apiService = BASE_URL_BIN_CHECKER.getApiService()
                ),
                TransactionNumberDataSource(
                    apiService = BASE_URL_TRANSACTION.getApiService()
                ),
                FinalizeTransactionDataSource(
                    apiService = BASE_URL_TRANSACTION.getApiService()
                )
            )
            return CardNumberViewModel(
                BinCheckerUseCase(repositoryImplBinChecker),
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
private fun String.getApiService(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)

        // Add interceptor here...
        .build()

    return Retrofit.Builder()
        .baseUrl(this)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}