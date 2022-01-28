package com.example.hw6

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.hw6.databinding.FragmentContactDetailsBinding


private const val CONTACT_KEY = "CONTACT_KEY"

class ContactDetailsFragment : Fragment(R.layout.fragment_contact_details) {

    private val binding by viewBinding(FragmentContactDetailsBinding::bind)
    private lateinit var currentContact: Contact


    private lateinit var contract: ContactDetailsContract

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val launchActivity = requireActivity()
        if (launchActivity is ContactDetailsContract) {
            contract = launchActivity
        } else {
            throw IllegalStateException("Launch activity should implement ContactDetailsContract")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentContact = arguments?.getParcelable(CONTACT_KEY)
            ?: throw IllegalStateException("Contact should be provided")
        fillData()
        initSaveButton()
    }

    private fun initSaveButton() {
        binding.saveChangesButton.setOnClickListener {
            val modifiedContact = currentContact.copy(
                number = binding.phoneEditText.text.toString(),
                name = binding.nameEditText.text.toString(),
                surname = binding.surnameEditText.text.toString()
            )
            contract.saveContact(
                currentContact,
                modifiedContact
            )
            currentContact = modifiedContact
            arguments?.putParcelable(CONTACT_KEY, currentContact)
        }
    }

    private fun fillData() {
        binding.nameEditText.setText(currentContact.name)
        binding.surnameEditText.setText(currentContact.surname)
        binding.phoneEditText.setText(currentContact.number)
        Glide
            .with(requireContext())
            .load(currentContact.imageUrl)
            .into(binding.contactImageView)
    }

    companion object {
        fun newInstance(contact: Contact): ContactDetailsFragment {
            val args = Bundle()
            args.putParcelable(CONTACT_KEY, contact)
            val fragment = ContactDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}