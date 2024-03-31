package com.example.compradecartera

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CustomToast {

    fun toast (context: Context, text: String){
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.layout_custom_toast, null)

        val textView = layout.findViewById<TextView>(R.id.textViewMessage)
        textView.text = text

        val imageView = layout.findViewById<ImageView>(R.id.imageViewIcon)
        imageView.setImageResource(R.drawable.credit_card)

        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()

    }
}