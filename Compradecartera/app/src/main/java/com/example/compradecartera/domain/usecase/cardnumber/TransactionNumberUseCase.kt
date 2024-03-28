package com.example.compradecartera.domain.usecase.cardnumber

import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.repository.UserRepository

class TransactionNumberUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): TransactionNumberResponse = userRepository.getTransactionNumber()
}