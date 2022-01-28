package com.example.hw6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hw6.databinding.ContactItemListBinding


class ContactViewHolder(parent: ViewGroup, listener: OnContactClickListener) : RecyclerView.ViewHolder(
    ContactItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
) {

    private val binding = ContactItemListBinding.bind(itemView)

    private lateinit var currentContact: Contact

    init {
        itemView.setOnClickListener {
            listener.onClick(currentContact)
        }
        itemView.setOnLongClickListener { view ->
            listener.onLongClick(currentContact, view)
            true
        }
    }

    fun bind(contact: Contact) {

        currentContact = contact
        binding.contactNameTextView.text = contact.name
        Glide
            .with(itemView.context)
            .load(contact.imageUrl)
            .into(binding.contactPhotoImageView)
    }
}