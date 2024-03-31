package com.example.compradecartera.presentation.ui.cardnumber

sealed class TransactionState {

    data class Success(val message: String) : TransactionState()

    data class Error(val messageError: String) : TransactionState()
}
