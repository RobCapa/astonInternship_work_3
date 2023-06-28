package com.example.work_3_1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.work_3_1.databinding.ItemCountryBinding

class CountryRecyclerAdapter(
    private val listCountries: List<Country>
): RecyclerView.Adapter<CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int = listCountries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(listCountries[position])
    }
}