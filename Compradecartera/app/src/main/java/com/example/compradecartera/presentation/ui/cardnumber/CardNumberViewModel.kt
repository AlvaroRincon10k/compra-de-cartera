package com.example.compradecartera.presentation.ui.cardnumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.BinCheckerResponse
import com.example.compradecartera.domain.usecase.cardnumber.BinCheckerUseCase
import com.example.compradecartera.domain.usecase.cardnumber.FinalizeTransactionUseCase
import kotlinx.coroutines.launch

internal class CardNumberViewModel(
    private val getBinCheckernUseCase: BinCheckerUseCase
): ViewModel() {

    private val finalizeTransactionMutableLiveData = MutableLiveData<FinalizeTransactionResponse>()
    val finalizeTransactionLiveData: LiveData<FinalizeTransactionResponse> = finalizeTransactionMutableLiveData

    fun getFinalizeTransaction(bin: String) {
        viewModelScope.launch {
            runCatching {
                val user = getBinCheckernUseCase.invoke(bin)
                finalizeTransactionMutableLiveData.value = user
            }.getOrElse {
                finalizeTransactionMutableLiveData.value = FinalizeTransactionResponse(it.message.orEmpty())
            }
        }
    }

}