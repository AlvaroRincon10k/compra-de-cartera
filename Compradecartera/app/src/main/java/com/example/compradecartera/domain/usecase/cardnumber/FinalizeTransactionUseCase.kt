package com.example.compradecartera.domain.usecase.cardnumber

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.domain.repository.CardNumberRepository
import com.example.compradecartera.domain.repository.UserRepository

class FinalizeTransactionUseCase(private val cardNumberRepository: CardNumberRepository) {

    suspend operator fun invoke(id:String): FinalizeTransactionResponse = cardNumberRepository.getFinalizeTransaction(id)
}