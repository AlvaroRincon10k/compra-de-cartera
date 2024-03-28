package com.example.compradecartera.presentation.ui.cardnumber

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import com.example.compradecartera.di.ViewModelFactoryTransactionNumber

class CardNumber : AppCompatActivity() {

    private lateinit var binding: ActivityCardNumberBinding
    //private lateinit var id:String
    private val viewModel: CardNumberViewModel by viewModels{
        ViewModelFactoryTransactionNumber()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserverTransactionNumber()
        initObserverFinalizeTransaction()
        viewModel.getTransactionNumber()
        //viewModel.getFinalizeTransaction(id)

        //binding.generarId.setOnClickListener { transactionNumber() }
    }

    private fun initObserverTransactionNumber() {
        viewModel.transactionNumberLiveData.observe(this) { id->
            binding.transactionNumber.text = id.id.toString()
        }
    }

    private fun initObserverFinalizeTransaction() {
        viewModel.transactionNumberLiveData.observe(this) { id->
            binding.transactionNumber.text = id.id.toString()
        }
    }

   /*
    private fun getRetrofitTransaccion(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/confirm/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun FinalizeTransaction(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofitTransaccion().create(ApiService::class.java).getFinalizeTransaction("$query")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    binding.finalizarTrasaccion.setText(puppies?.message.toString())
                } else {
                    Toast.makeText(this@CardNumber, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }*/
}