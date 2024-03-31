package com.example.compradecartera.presentation.ui.cardnumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.domain.usecase.cardnumber.BinCheckerUseCase
import kotlinx.coroutines.launch

internal class CardNumberViewModel(
    private val getBinCheckernUseCase: BinCheckerUseCase
): ViewModel() {

    private val transactionStateMutableLiveData = MutableLiveData<TransactionState>()
    val transactionStateLiveData: LiveData<TransactionState> = transactionStateMutableLiveData

    fun getFinalizeTransaction(bin: String) {
        viewModelScope.launch {
            runCatching {
                val user = getBinCheckernUseCase.invoke(bin)
                transactionStateMutableLiveData.value = TransactionState.Success(user.message)
            }.getOrElse {
                val message = if (!it.message.isNullOrEmpty()){
                    it.message
                } else "Ha ocurrido un error"
                transactionStateMutableLiveData.value = TransactionState.Error(message.orEmpty())
            }
        }
    }

}