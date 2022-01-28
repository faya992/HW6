package com.example.hw6

import android.view.View

interface OnContactClickListener {
    fun onClick(contact: Contact)
    fun onLongClick(contact: Contact, anchorView: View)
}