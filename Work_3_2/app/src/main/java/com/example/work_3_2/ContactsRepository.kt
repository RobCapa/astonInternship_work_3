package com.example.work_3_2

import kotlin.random.Random
import kotlin.random.nextUInt


class ContactsRepository {
    private val contacts = mutableListOf<Contact>()

    init {
        (1..100).forEach {
            val contact = Contact(
                it,
                "lastName",
                "firstName $it",
                Random.nextUInt().toString()
            )
            contacts.add(contact)
        }
    }

    fun getContacts(): List<Contact> {
        return contacts.map { it.copy() }
    }

    fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    fun updateContact(contact: Contact) {
        val oldContact = contacts.first { it.id == contact.id }
        val index = contacts.indexOf(oldContact)
        contacts[index] = contact
    }

    fun removeContact(contactId: Int): Boolean {
        return contacts.removeIf { it.id == contactId }
    }

    fun removeContacts(contactsId: List<Int>): Boolean {
        return contacts.removeIf { contactsId.contains(it.id) }
    }

    fun moveContact(oldPosition: Int, newPosition: Int) {
        val contact = contacts[oldPosition]
        contacts.remove(contact)
        contacts.add(newPosition, contact)
    }
}