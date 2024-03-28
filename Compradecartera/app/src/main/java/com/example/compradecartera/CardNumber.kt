package com.example.compradecartera

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import com.example.compradecartera.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CardNumber : AppCompatActivity() {

    private lateinit var binding: ActivityCardNumberBinding

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
                        binding.transactionNumber.setText(puppies?.id.toString())
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
}