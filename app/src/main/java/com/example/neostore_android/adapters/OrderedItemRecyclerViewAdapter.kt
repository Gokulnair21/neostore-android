package com.example.neostore_android.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neostore_android.databinding.OrderedItemCardBinding


class OrderedItemRecyclerViewAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<OrderedItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            OrderedItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()

    }

    override fun getItemCount(): Int = 8

    inner class ViewHolder(
        var binding: OrderedItemCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData() {
            binding.orderItemTitle.text = "Demo"
            binding.orderItemType.text = "(" + "Table" + ")"
            binding.orderItemQuantity.text = "QTY : " + "3"
            binding.orderItemCost.text = "₹40.00"

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}