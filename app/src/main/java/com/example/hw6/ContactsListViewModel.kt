package com.example.hw6

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import io.github.serpro69.kfaker.faker
import kotlin.collections.ArrayList

class ContactsListViewModel : ViewModel() {

    private val faker = faker { }

    private val contactsList = generateContactsList()

    val setContactsLiveData: LiveData<List<Contact>> = MutableLiveData()
    val openContactDetailLiveData: LiveData<Event<Contact>> = MutableLiveData()
    val dispatchDiffUtilLiveData: LiveData<Event<DiffUtil.DiffResult>> = MutableLiveData()

    fun onViewIsReady() {
        setContactsLiveData.postValue(contactsList)
    }

    private fun generateContactsList(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()
        for (id in 1..100) {
            contacts += Contact(
                faker.phoneNumber.phoneNumber(),
                faker.name.firstName(),
                faker.name.lastName(),
                "https://picsum.photos/id/$id/200/200",
                id
            )
        }
        return contacts
    }

    fun onContactClick(contact: Contact) {
        openContactDetailLiveData.postValue(Event(contact))
    }

    fun onContactLongClick(contact: Contact) {
        val oldContacts = ArrayList(contactsList)
        contactsList -= contact
        updateListWithDiffUtil(oldContacts, contactsList)
    }

    private fun updateListWithDiffUtil(
        oldContacts: List<Contact>,
        newContacts: List<Contact>
    ) {
        val diffUtil = ContactsDiffUtil(oldContacts, newContacts)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        dispatchDiffUtilLiveData.postValue(Event(diffResult))
    }

    fun saveContact(oldContact: Contact, newContact: Contact) {
        val oldContacts = ArrayList(contactsList)
        val oldContactIndex = oldContacts.indexOf(oldContact)
        contactsList[oldContactIndex] = newContact
        updateListWithDiffUtil(oldContacts, contactsList)
    }
}

private fun <T> LiveData<T>.postValue(value: T) {
    (this as MutableLiveData<T>).postValue(value)
}