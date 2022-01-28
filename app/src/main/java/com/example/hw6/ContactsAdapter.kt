package com.example.hw6

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ContactsAdapter : RecyclerView.Adapter<ContactViewHolder>() {

    var contactsList = emptyList<Contact>()
        set(value) {
            field = value
            filteredContactsList = value
        }
    private var filteredContactsList = contactsList
    lateinit var listener: OnContactClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(filteredContactsList[position])
    }

    override fun getItemCount() = filteredContactsList.size

    fun filterList(text: String) {
        val currentFilteredList = ArrayList(filteredContactsList)
        filteredContactsList = if (text.isEmpty()) {
            contactsList
        } else {
            val filteredList = arrayListOf<Contact>()
            val textLowerCased = text.lowercase(Locale.getDefault())
            for (contact in contactsList) {
                if (contact.name.lowercase(Locale.getDefault()).contains(textLowerCased)
                ) {
                    filteredList.add(contact)
                }
            }
            filteredList
        }
        val diffUtil = ContactsDiffUtil(currentFilteredList, filteredContactsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)
    }
}