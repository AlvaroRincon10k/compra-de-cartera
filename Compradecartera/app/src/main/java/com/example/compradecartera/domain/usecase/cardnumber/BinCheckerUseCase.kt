package com.example.compradecartera.domain.usecase.cardnumber

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.BinCheckerResponse
import com.example.compradecartera.domain.repository.CardNumberRepository
import com.example.compradecartera.domain.repository.UserRepository

class BinCheckerUseCase(private val cardNumberRepository: CardNumberRepository) {

    suspend operator fun invoke(bin:String): FinalizeTransactionResponse {
        val bin = cardNumberRepository.getBinChecker(bin)

        if (bin.BIN.country.name == "COLOMBIA") {
           val transactionResponse = cardNumberRepository.getTransactionNumber()
           return cardNumberRepository.getFinalizeTransaction(transactionResponse.id.toString())
        } else {
            error("Sorry, it is not colombia target")
        }
    }
}