package com.alexius.mikatsu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val recipeName: String,
    val author: String,
    val description: String,
    val photo: Int,
    val ingredients: String
) : Parcelable
