package com.example.compradecartera.presentation.ui.cardnumber

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.compradecartera.CustomToast
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
    private var finalizeTransaction: Boolean = false
    private lateinit var title: String
    private lateinit var titleMessage: String
    private var cancelable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCardNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Se le asigna un color personalizado al progressBar
        binding.progressBar.indeterminateTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.background_color))

        initObservers()

        // Botón de finalizar la transacción
        binding.buttonFinalizarTransaccion.setOnClickListener {
            hideKeyboard()
            validations(binding.editTextValue, binding.editTextCardNumber)
        }

        amount = intent.extras?.getDouble(AMOUNT_KEY)!!
        binding.textViewAvailableCredit.text = amount.toString()
        SelectedValue(binding.editTextValue)
        addSeparatorFourDigits(binding.editTextCardNumber)
    }

    private fun initObservers() {
        viewModel.transactionStateLiveData.observe(this) {
            binding.progressBar.visibility = View.GONE
            // Desbloquear el botón
            binding.buttonFinalizarTransaccion.isEnabled = true
            validateAlertDialog(it)
            confirmation()
            if (finalizeTransaction) timerFinish()
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

    //  Función para extraer el BIN de la tarjeta
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
            editTextAmount.error = getString(R.string.empty_amount)
        } else if (amountBuy > amount) {
            editTextAmount.error = getString(R.string.greater_amount)
        } else {
            // El monto a comprar es válido
            // Obtener el número de tarjeta ingresado
            val cardNumber: String =
                (binding.editTextCardNumber.text.toString()).replace("\\s".toRegex(), "")

            // Validar la longitud del número de tarjeta
            if (cardNumber.length !in 15..16) {
                editTextCardNumber.error = (getString(R.string.validate_credit_card))
            } else {
                binding.progressBar.visibility = View.VISIBLE
                // Bloquear el botón
                binding.buttonFinalizarTransaccion.isEnabled = false
                CustomToast().toast(this, getString(R.string.end_transaction_button_blocked))
                // Se eliminan los espacios al extraer el número de tarjeta
                val cardNumer =
                    (binding.editTextCardNumber.text.toString()).replace("\\s".toRegex(), "")
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

    //Función para ocultar el teclado
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { view ->
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // Función para crear un AlertDialog
    private fun confirmation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setCancelable(cancelable)
        builder.setMessage(titleMessage)
        val dialog = builder.create()
        dialog.show()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Finalizar el AlertDialog después de 6 segundos
            dialog.dismiss()
        }, 6000)
    }

    //Validar la respuesta obtenida
    private fun validateAlertDialog(transactionState: TransactionState) {
        when (transactionState) {
            is TransactionState.Error -> {
                cancelable = true
                finalizeTransaction = false
                title = transactionState.messageError
                titleMessage = getString(R.string.try_again)
            }

            is TransactionState.Success -> {
                cancelable = false
                finalizeTransaction = true
                title = transactionState.message
                titleMessage = getString(R.string.title_message)
            }
        }
    }

    // Temporizador para retrasar la finalización de la actividad
    private fun timerFinish() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Finalizar la actividad después de 6 segundos
            returnResult()
        }, 6000)
    }

    private fun returnResult() {
        val resultIntent = Intent()
        resultIntent.putExtra(AMOUNT_KEY, binding.editTextValue.text.toString().toDouble())
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}