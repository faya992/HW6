package com.example.hw6

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Contact(
    var number: String,
    var name: String,
    var surname: String,
    var imageUrl: String = "",
    val id: Int
) : Parcelable