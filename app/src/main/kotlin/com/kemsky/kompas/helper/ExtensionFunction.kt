package com.kemsky.kompas.helper

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

fun Number?.hectogramsToKilograms(): Double? {
    return this?.toDouble()?.div(10)
}

fun Number?.decimetersToMeters(): Double? {
    return this?.toDouble()?.div(10)
}