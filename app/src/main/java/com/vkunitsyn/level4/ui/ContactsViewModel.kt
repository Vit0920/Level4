package com.vkunitsyn.level4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkunitsyn.level4.model.Contact
import com.vkunitsyn.level4.utils.ContactsData

class ContactsViewModel : ViewModel() {

    private val _contactsList = MutableLiveData<List<Contact>>()
    val contactsList: LiveData<List<Contact>> = _contactsList


    init {
        _contactsList.value = ContactsData.getData()
    }

    fun add(position: Int, contact: Contact) {
        val currentContactsList = _contactsList.value ?: emptyList()
        val newContactsList = currentContactsList.toMutableList()
        newContactsList.add(position, contact)
        _contactsList.value = newContactsList
    }

    fun remove(contact: Contact) {
        val currentContactsList = _contactsList.value ?: emptyList()
        val newContactsList = currentContactsList.toMutableList()
        newContactsList.remove(contact)
        _contactsList.value = newContactsList
    }

    fun removeAt(position: Int) {
        val currentContactsList = _contactsList.value ?: emptyList()
        val newContactsList = currentContactsList.toMutableList()
        newContactsList.removeAt(position)
        _contactsList.value = newContactsList
    }

    fun get(position: Int): Contact? = _contactsList.value?.get(position)


    fun getSize(): Int? = _contactsList.value?.size
}