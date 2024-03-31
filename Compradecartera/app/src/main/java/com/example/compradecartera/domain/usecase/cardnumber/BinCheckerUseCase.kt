package com.example.compradecartera.domain.usecase.cardnumber

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.domain.repository.CardNumberRepository

class BinCheckerUseCase(private val cardNumberRepository: CardNumberRepository) {

    suspend operator fun invoke(bin: String): FinalizeTransactionResponse {
        val bin = cardNumberRepository.getBinChecker(bin)
        val countryCapitalize = bin.BIN.country.name.lowercase().capitalize()
        if (bin.BIN.country.name == "COLOMBIA") {
            val transactionResponse = cardNumberRepository.getTransactionNumber()
            val finalizeTransactionResponse =
                cardNumberRepository.getFinalizeTransaction(transactionResponse.id.toString())
            return finalizeTransactionResponse.copy(message = "${finalizeTransactionResponse.message} con tu tarjeta de $countryCapitalize")
        } else {
            error("Tu tarjeta es de $countryCapitalize, por favor intenta con una tarjeta de Colombia.")
        }
    }
}