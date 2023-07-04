package com.example.work_3_2

import androidx.recyclerview.widget.RecyclerView
import com.example.work_3_2.databinding.ItemContactBinding

class ContactViewHolder(
    val binding: ItemContactBinding,
    private val onClickOnItemCallback: (Contact) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact) {
        binding.root.setOnClickListener { onClickOnItemCallback(contact) }
        binding.root.setBackgroundResource(0)

        with(contact) {
            binding.itemContactTextViewName.text = "$lastName $firstName"
            binding.itemContactTextViewNumber.text = number
        }
    }
}