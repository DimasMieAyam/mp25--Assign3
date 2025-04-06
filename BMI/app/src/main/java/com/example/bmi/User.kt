package com.example.bmi

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class User (
    val username: String,
    val email: String,
    val password: String
) : Parcelable