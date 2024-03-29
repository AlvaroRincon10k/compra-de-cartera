package com.example.compradecartera.presentation.ui.cardnumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.usecase.cardnumber.FinalizeTransactionUseCase
import com.example.compradecartera.domain.usecase.cardnumber.TransactionNumberUseCase
import kotlinx.coroutines.launch

internal class CardNumberViewModel(
    private val getTransactionNumberUseCase: TransactionNumberUseCase,
    private val getFinalizeTransactionUseCase: FinalizeTransactionUseCase
): ViewModel() {

    private val transactionNumberMutableLiveData = MutableLiveData<TransactionNumberResponse>()
    val transactionNumberLiveData: LiveData<TransactionNumberResponse> = transactionNumberMutableLiveData

    private val finalizeTransactionMutableLiveData = MutableLiveData<FinalizeTransactionResponse>()
    val finalizeTransactionLiveData: LiveData<FinalizeTransactionResponse> = finalizeTransactionMutableLiveData

    fun getTransactionNumber() {
        viewModelScope.launch {
            runCatching {
                val user = getTransactionNumberUseCase.invoke()
                transactionNumberMutableLiveData.value = user
            }.getOrElse {
                // emitir un eror y mostrarlo
            }
        }
    }

    fun getFinalizeTransaction(id: String) {
        viewModelScope.launch {
            runCatching {
                val user = getFinalizeTransactionUseCase.invoke(id)
                finalizeTransactionMutableLiveData.value = user
            }.getOrElse {
                // emitir un eror y mostrarlo
            }
        }
    }

}