package com.example.work_3_1

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.work_3_1.databinding.ItemCountryBinding

class CountryViewHolder(
    private val binding: ItemCountryBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        with(country) {
            binding.itemCountryTextViewName.text = name

            Glide.with(binding.root)
                .load(flagUrl)
                .placeholder(R.drawable.baseline_flag)
                .into(binding.itemCountryImageViewFlag)
        }
    }
}