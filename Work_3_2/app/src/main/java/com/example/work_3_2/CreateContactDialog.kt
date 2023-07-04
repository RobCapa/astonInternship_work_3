package com.example.work_3_2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.example.work_3_2.databinding.DialogEditContactBinding
import kotlin.random.Random

class CreateContactDialog(
    private val positiveCallback: (Contact) -> Unit,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogEditContactBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireActivity())
            .setTitle("Contact")
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                val lastName = binding.dialogEditContactEditTextLastName.text.toString()
                val firstName = binding.dialogEditContactEditTextFirstName.text.toString()
                val number = binding.dialogEditContactEditPhone.text.toString()

                val contact = Contact(
                    Random.nextInt(),
                    lastName,
                    firstName,
                    number
                )

                positiveCallback(contact)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
    }
}