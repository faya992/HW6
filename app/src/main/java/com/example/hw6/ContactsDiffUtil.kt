package com.example.hw6

import androidx.recyclerview.widget.DiffUtil

class ContactsDiffUtil(
    private val oldContacts: List<Contact>,
    private val newContacts: List<Contact>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldContacts.size
    }

    override fun getNewListSize(): Int {
        return newContacts.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContacts[oldItemPosition].id == newContacts[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldContacts[oldItemPosition]
        val newContact = newContacts[newItemPosition]
        return oldContact.name == newContact.name
    }
}