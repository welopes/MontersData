package com.example.montersdata.util

import android.widget.ImageView
import android.widget.TextView
import java.text.NumberFormat

class BindAdapters {

    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }

    @BindingAdapter("price")
    fun itemPrice(view: TextView, value: Double) {
        val formatter = NumberFormat.getCurrencyInstance()
        val text = "${formatter.format(value)} / each"
        view.text = text
    }
}