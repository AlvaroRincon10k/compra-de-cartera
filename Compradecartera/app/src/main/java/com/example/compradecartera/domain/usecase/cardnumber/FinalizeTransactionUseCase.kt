package com.example.compradecartera.domain.usecase.cardnumber

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.domain.repository.UserRepository

class FinalizeTransactionUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(id:String): FinalizeTransactionResponse = userRepository.getFinalizeTransaction(id)
}