package com.example.contactlist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactlist.models.Contact
import com.example.contactlist.models.ContactDatabase

class MainActivityViewModel: ViewModel() {
    val contactsLiveData = MutableLiveData<List<Contact>>()

    fun getContacts(database: ContactDatabase){
        contactsLiveData.postValue(database.contactDAO().getAllContacts())
    }

    fun addContact(database: ContactDatabase, contact: Contact){
        database.contactDAO().addContact(contact)
        getContacts(database)

    }
}