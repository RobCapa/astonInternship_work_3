package com.example.work_3_2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.work_3_2.databinding.DialogEditContactBinding
import kotlin.random.Random

class EditContactDialog(
    private val oldContact: Contact,
    private val positiveCallback: (Contact) -> Unit,
    private val removeCallback: (Contact) -> Unit,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogEditContactBinding.inflate(LayoutInflater.from(context))

        binding.dialogEditContactEditTextLastName.setText(oldContact.lastName)
        binding.dialogEditContactEditTextFirstName.setText(oldContact.firstName)
        binding.dialogEditContactEditPhone.setText(oldContact.number)
        binding.dialogEditContactImageButtonDelete.isVisible = true
        binding.dialogEditContactImageButtonDelete.setOnClickListener {
            removeCallback(oldContact)
            dismiss()
        }

        return AlertDialog.Builder(requireActivity())
            .setTitle("Contact")
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                val lastName = binding.dialogEditContactEditTextLastName.text.toString()
                val firstName = binding.dialogEditContactEditTextFirstName.text.toString()
                val number = binding.dialogEditContactEditPhone.text.toString()

                val contact = oldContact.copy(
                    lastName = lastName,
                    firstName = firstName,
                    number = number
                )

                positiveCallback(contact)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }
}