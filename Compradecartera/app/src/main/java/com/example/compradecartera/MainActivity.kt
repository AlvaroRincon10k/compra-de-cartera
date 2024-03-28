package com.example.compradecartera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.compradecartera.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val queryName: String = "alvaro"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name(queryName)

        binding.comprar.setOnClickListener { navigateCardNumber() }

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.mocki.io/v2/0ms8mu9j/user/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun name(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getUser("$query")
            val puppies = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    binding.name.setText(puppies?.name.toString())
                    binding.amout.setText(puppies?.amount.toString())
                    binding.cardNumber.setText(puppies?.card_number.toString())
                } else {
                    Toast.makeText(binding.name.context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateCardNumber() {
        val intent = Intent(this, CardNumber::class.java)
        startActivity(intent)
    }
}