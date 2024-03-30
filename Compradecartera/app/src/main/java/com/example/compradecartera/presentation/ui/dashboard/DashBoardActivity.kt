package com.example.compradecartera.presentation.ui.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.compradecartera.R
import com.example.compradecartera.databinding.ActivityMainBinding
import com.example.compradecartera.di.ViewModelFactory
import com.example.compradecartera.presentation.ui.cardnumber.CardNumberActivity

const val QUERY_NAME: String = "alvaro"

class DashBoardActivity : AppCompatActivity() {

    private var amount: Double = 0.0
    private lateinit var binding: ActivityMainBinding
    private val viewModel: DashBoardViewModel by viewModels {
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
        viewModel.userLiveData.observe(this) { user ->
            amount = user.amount
            binding.textViewUserName.text = user.name
            binding.textViewBalance.text = amount.toString()
            binding.textViewCardNumber.text = user.card_number
        }
    }

    private fun navigateCardNumber() {
        val intent = Intent(this, CardNumberActivity::class.java)
        intent.putExtra("AMOUNT", amount)
        startActivity(intent)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Salir de la aplicación")
        builder.setMessage("¿Estás seguro de que quieres salir?")
        builder.setPositiveButton("Sí") { dialog, which ->
            // Cierra la actividad y sale de la aplicación
            super.onBackPressed()
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Cancela el diálogo y no realiza ninguna acción
            dialog.dismiss()
        }

        val dialog = builder.create()

        // Cambiar el color a los botones "si" y "no"
        dialog.setOnShowListener { dialog ->
            val positiveButton = (dialog as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
            val negativeButton = (dialog as AlertDialog).getButton(DialogInterface.BUTTON_NEGATIVE)

            positiveButton.setTextColor(ContextCompat.getColor(this, R.color.background_color))
            negativeButton.setTextColor(ContextCompat.getColor(this, R.color.background_color))
        }

        dialog.show()
    }
}