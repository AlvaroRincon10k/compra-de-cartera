package com.example.compradecartera.presentation.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compradecartera.databinding.ActivityMainBinding
import com.example.compradecartera.di.ViewModelFactory
import com.example.compradecartera.presentation.ui.cardnumber.CardNumber

const val QUERY_NAME: String = "alvaro"

class DashBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DashBoardViewModel by viewModels{
        ViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        viewModel.getUser(QUERY_NAME)

        binding.buttonBuyDebt.setOnClickListener { navigateCardNumber() }
    }

    private fun initObserver() {
        viewModel.userLiveData.observe(this) { user->
            binding.textViewUserName.text = user.name
            binding.textViewBalance.text = user.amount.toString()
            binding.textViewCardNumber.text = user.card_number
        }
    }

    private fun navigateCardNumber() {
        val intent = Intent(this, CardNumber::class.java)
        startActivity(intent)
    }
}