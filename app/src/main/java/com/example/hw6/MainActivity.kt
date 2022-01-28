package com.example.hw6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity(), ContactClickedListener, ContactDetailsContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            startContactsListFragment()
        }
    }

    private fun startContactsListFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.contacts_list_fragment_container,
                ContactsListFragment()
            )
            .commit()
    }

    override fun openContactDetails(contact: Contact) {
        supportFragmentManager
            .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.contact_details_fragment_container,
                ContactDetailsFragment.newInstance(contact)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun saveContact(oldContact: Contact, newContact: Contact) {
        val contactsListFragment = supportFragmentManager
            .findFragmentById(R.id.contacts_list_fragment_container) as ContactsListFragment
        contactsListFragment.saveContact(oldContact, newContact)
    }
}