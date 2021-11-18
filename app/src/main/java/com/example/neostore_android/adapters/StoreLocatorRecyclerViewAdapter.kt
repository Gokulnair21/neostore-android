package com.example.neostore_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.databinding.StoreLocatorCardBinding


class StoreLocatorRecyclerViewAdapter(
) : RecyclerView.Adapter<StoreLocatorRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            StoreLocatorCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount(): Int = 4

    inner class ViewHolder(
        var binding: StoreLocatorCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData() {

        }
    }
}
