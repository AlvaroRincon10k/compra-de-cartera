package com.example.compradecartera.presentation.ui.cardnumber

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import com.example.compradecartera.di.ViewModelFactoryCardNumber

class CardNumber : AppCompatActivity() {

    private lateinit var binding: ActivityCardNumberBinding
    private lateinit var idTransactionNumber:String
    private val viewModel: CardNumberViewModel by viewModels{
        ViewModelFactoryCardNumber()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserverTransactionNumber()
        initObserverFinalizeTransaction()
        viewModel.getTransactionNumber()

        binding.buttonFinalizarTransaccion.setOnClickListener { viewModel.getFinalizeTransaction(idTransactionNumber) }
    }

    private fun initObserverTransactionNumber() {
        viewModel.transactionNumberLiveData.observe(this) { id->
            idTransactionNumber = id.id.toString()
            //binding.transactionNumber.text = idTransactionNumber
        }
    }

    private fun initObserverFinalizeTransaction() {
        viewModel.finalizeTransactionLiveData.observe(this) { message->
            //binding.finalizarTrasaccion.text = message.message
            Toast.makeText(this, message.message, Toast.LENGTH_SHORT).show()
        }
    }
}