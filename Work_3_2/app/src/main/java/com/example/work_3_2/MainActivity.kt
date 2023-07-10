package com.example.work_3_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.work_3_2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()
    private val contactAdapter = ContactRecyclerAdapter(::showEditContactDialog, ::showRemoveButton)
    private val contactRepository = ContactsRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(binding.activityMainRecyclerView) {
            adapter = contactAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        binding.activityMainFloatingButtonAddContact.setOnClickListener {
            showCreateContactDialog()
        }

        binding.activityMainFloatingButtonRemoveContacts.setOnClickListener {
            val contactsId = contactAdapter.getSelectedItems().map { it.id }
            contactRepository.removeContacts(contactsId)
            showRemoveButton(false)
            refreshContactsInRecycler()
        }

        refreshContactsInRecycler()

        val callback: ItemTouchHelper.Callback = DragItemHelperCallback(
            contactAdapter,
            ::moveContact,
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.activityMainRecyclerView)
    }

    private fun moveContact(oldPosition: Int, newPosition: Int) {
        contactRepository.moveContact(oldPosition, newPosition)
    }

    private fun showRemoveButton(show: Boolean) {
        binding.activityMainFloatingButtonRemoveContacts.isVisible = show
    }

    private fun showCreateContactDialog() {
        CreateContactDialog(::addContact).show(
            supportFragmentManager,
            CREATE_CONTACT_DIALOG_TAG,
        )
    }

    private fun showEditContactDialog(contact: Contact) {
        EditContactDialog(
            contact,
            ::updateContact,
            ::removeContact,
        ).show(
            supportFragmentManager,
            EDIT_CONTACT_DIALOG_TAG,
        )
    }

    private fun updateContact(contact: Contact) {
        contactRepository.updateContact(contact)
        refreshContactsInRecycler()
    }

    private fun addContact(contact: Contact) {
        contactRepository.addContact(contact)
        refreshContactsInRecycler()
    }

    private fun removeContact(contact: Contact) {
        contactRepository.removeContact(contact.id)
        refreshContactsInRecycler()
    }

    private fun refreshContactsInRecycler() {
        val contacts = contactRepository.getContacts()
        contactAdapter.updateItemsList(contacts)
    }

    companion object {
        private const val CREATE_CONTACT_DIALOG_TAG = "CREATE_CONTACT_DIALOG_TAG"
        private const val EDIT_CONTACT_DIALOG_TAG = "EDIT_CONTACT_DIALOG_TAG"
    }
}