package com.example.seavox

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AbecedarioItem(
    val imageResId: Int,
    val title: String,
    val description: String
) : Parcelable
