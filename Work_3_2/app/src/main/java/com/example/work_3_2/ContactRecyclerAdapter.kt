package com.example.work_3_2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.work_3_2.databinding.ItemContactBinding
import java.util.Collections


class ContactRecyclerAdapter(
    private val onClickOnItemCallback: (Contact) -> Unit,
    private val onLongClickListener: (Boolean) -> Unit,
) : RecyclerView.Adapter<ContactViewHolder>(), ItemTouchHelperAdapter {
    private val differ = AsyncListDiffer(this, diffContact)
    private var isMultipleChoiceModeActive = false
    private var selectedItems = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ContactViewHolder(binding) { contact ->
            if (isMultipleChoiceModeActive) {
                if (selectedItems.contains(contact)) {
                    unselectItem(binding.root, contact)
                    if (selectedItems.isEmpty()) setMultipleChoiceMode(false)
                } else {
                    selectItem(binding.root, contact)
                }
            } else {
                onClickOnItemCallback(contact)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = differ.currentList[position]

        holder.binding.root.setOnLongClickListener {
            if (!isMultipleChoiceModeActive) {
                selectItem(holder.binding.root, contact)
                setMultipleChoiceMode(true)
                true
            } else {
                false
            }
        }

        holder.bind(contact)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val newList = differ.currentList.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(newList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(newList, i, i - 1)
            }
        }
        updateItemsList(newList)
    }

    override fun onItemDismiss(position: Int) {
        with(differ.currentList) {
            val contact = get(position)
            remove(contact)
        }
    }

    fun getSelectedItems(): List<Contact> {
        isMultipleChoiceModeActive = false
        val selectedItemsCopy = selectedItems.map { it }
        selectedItems = mutableListOf()
        return selectedItemsCopy
    }

    fun updateItemsList(items: List<Contact>) {
        differ.submitList(items)
    }

    private fun setMultipleChoiceMode(isActive: Boolean) {
        isMultipleChoiceModeActive = isActive
        onLongClickListener(isActive)
    }

    private fun selectItem(view: View, contact: Contact) {
        selectedItems.add(contact)
        view.setBackgroundResource(R.color.gris)
    }

    private fun unselectItem(view: View, contact: Contact) {
        selectedItems.remove(contact)
        view.setBackgroundResource(0)
    }

    companion object {
        private val diffContact = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(
                oldItem: Contact,
                newItem: Contact
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Contact,
                newItem: Contact
            ) = oldItem == newItem
        }
    }
}