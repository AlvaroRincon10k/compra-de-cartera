package com.example.compradecartera.presentation.ui.cardnumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import com.example.compradecartera.di.ViewModelFactoryCardNumber

class CardNumber : AppCompatActivity() {

    private var amountBuy: Double = 0.0
    private var amount: Double = 0.0

    private lateinit var binding: ActivityCardNumberBinding
    private lateinit var idTransactionNumber: String
    private val viewModel: CardNumberViewModel by viewModels {
        ViewModelFactoryCardNumber()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserverTransactionNumber()
        initObserverFinalizeTransaction()
        initObserverBinChecker()
        viewModel.getTransactionNumber()

        binding.buttonFinalizarTransaccion.setOnClickListener {
            viewModel.getFinalizeTransaction(idTransactionNumber)
        }

        amount = intent.extras?.getDouble("AMOUNT")!!
        binding.textViewAvailableCredit.text = amount.toString()
        SelectedValue()
    }

    private fun initObserverTransactionNumber() {
        viewModel.transactionNumberLiveData.observe(this) { id ->
            idTransactionNumber = id.id.toString()
            //binding.transactionNumber.text = idTransactionNumber
        }
    }

    private fun initObserverFinalizeTransaction() {
        viewModel.finalizeTransactionLiveData.observe(this) { message ->
            val message:String = message.message
            validations(message)
        }
    }

    private fun initObserverBinChecker() {
        viewModel.finalizeTransactionLiveData.observe(this) { message ->
        }
    }

    private fun SelectedValue() {
        binding.editTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.textViewSelectedValue.text = p0.toString()
            }

        })
    }

    fun cardLength(cardNumber: String): Boolean {
        val longitud = cardNumber.length
        return longitud in 15..16
    }

    fun getBin(cardNumber: String): String {
        return if (cardNumber.length >= 6) {
            cardNumber.substring(0, 6)
        } else {
            ""
        }
    }

    fun validations(message: String) {

        // Obtener el monto a comprar
        val amountBuy = binding.editTextValue.text.toString().toDoubleOrNull()

// Validar que el monto a comprar sea válido y esté dentro del saldo actual del usuario
        if (amountBuy == null || amountBuy <= 0) {
            Toast.makeText(
                this,
                "Ingrese un monto válido para la compra.",
                Toast.LENGTH_SHORT
            ).show()
        } else if (amountBuy > amount) {
            Toast.makeText(
                this,
                "El monto a comprar excede el saldo actual del usuario.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            // El monto a comprar es válido
            // Obtener el número de tarjeta ingresado
            val cardNumber: String = binding.editTextCardNumber.text.toString()

            // Validar la longitud del número de tarjeta
            if (cardNumber.length !in 15..16) {
                Toast.makeText(
                    this,
                    "Ingrese un número de tarjeta válido (15-16 números).",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}