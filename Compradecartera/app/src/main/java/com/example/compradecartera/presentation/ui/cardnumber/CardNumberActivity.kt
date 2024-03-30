package com.example.compradecartera.presentation.ui.cardnumber

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.compradecartera.R
import com.example.compradecartera.databinding.ActivityCardNumberBinding
import com.example.compradecartera.di.ViewModelFactoryCardNumber

internal const val AMOUNT_KEY = "AMOUNT"

class CardNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCardNumberBinding
    private val viewModel: CardNumberViewModel by viewModels {
        ViewModelFactoryCardNumber()
    }

    private var amount: Double = 0.0
    private var bin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.indeterminateTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.background_color))

        initObservers()

        // Botón de finalizar la transacción
        binding.buttonFinalizarTransaccion.setOnClickListener {
            validations(binding.editTextValue, binding.editTextCardNumber)
        }

        amount = intent.extras?.getDouble(AMOUNT_KEY)!!
        binding.textViewAvailableCredit.text = amount.toString()
        SelectedValue(binding.editTextValue)
        addSeparatorFourDigits(binding.editTextCardNumber)
    }

    private fun initObservers() {
        viewModel.finalizeTransactionLiveData.observe(this) { message ->
            binding.progressBar.visibility = View.GONE
            Toast.makeText(this, message.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun SelectedValue(textView: TextView) {
        textView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.textViewSelectedValue.text = p0.toString()
            }

        })
    }

    fun getBin(cardNumber: String): String {
        return if (cardNumber.length >= 6) {
            cardNumber.substring(0, 6)
        } else {
            ""
        }
    }

    fun validations(editTextAmount: EditText, editTextCardNumber: EditText) {

        // Obtener el monto a comprar
        val amountBuy = binding.editTextValue.text.toString().toDoubleOrNull()

        // Validar que el monto a comprar sea válido y esté dentro del saldo actual del usuario
        if (amountBuy == null || amountBuy <= 0) {
            editTextAmount.error = "Ingrese un monto válido para la compra."
        } else if (amountBuy > amount) {
            editTextAmount.error = ("El monto a comprar excede el saldo actual del usuario.")
        } else {
            // El monto a comprar es válido
            // Obtener el número de tarjeta ingresado
            val cardNumber: String =
                (binding.editTextCardNumber.text.toString()).replace("\\s".toRegex(), "")

            // Validar la longitud del número de tarjeta
            if (cardNumber.length !in 15..16) {
                editTextCardNumber.error = ("Ingrese un número de tarjeta válido (15-16 números).")
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val cardNumer = (binding.editTextCardNumber.text.toString()).replace("\\s".toRegex(), "")
                viewModel.getFinalizeTransaction(getBin(cardNumer))
            }
        }
    }

    private fun addSeparatorFourDigits(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private val SPACE = ' '

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val cleaned = it.toString().replace(SPACE.toString(), "")
                    if (cleaned.length > 0 && cleaned.length % 4 == 0) {
                        val formatted = StringBuilder()
                        for (i in cleaned.indices) {
                            if (i % 4 == 0 && i > 0) {
                                formatted.append(SPACE)
                            }
                            formatted.append(cleaned[i])
                        }
                        editText.removeTextChangedListener(this)
                        editText.setText(formatted.toString())
                        editText.setSelection(formatted.length)
                        editText.addTextChangedListener(this)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}