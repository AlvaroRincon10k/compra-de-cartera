package com.example.compradecartera.presentation.ui.cardnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.compradecartera.data.remote.ApiService
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardNumber : AppCompatActivity() {

    private lateinit var binding: ActivityCardNumberBinding
    private var id:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generarId.setOnClickListener { transactionNumber() }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun transactionNumber(){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                val call = getRetrofit().create(ApiService::class.java).getTransactionNumber()
                val puppies = call.body()
                runOnUiThread{
                    if(call.isSuccessful){
                        id = puppies?.id!!
                        binding.transactionNumber.setText(id)
                        binding.finalizar.setOnClickListener { FinalizeTransaction(id.toString()) }
                    }else{
                        Toast.makeText(this@CardNumber, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@CardNumber, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.i("error", e.message.toString())
                }
            }
        }
    }

    private fun FinalizeTransaction(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getFinalizeTransaction("$query")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    binding.finalizarTrasaccion.setText(puppies?.message.toString())
                } else {
                    Toast.makeText(this@CardNumber, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}