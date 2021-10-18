package com.example.neostore_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.databinding.OrderCardBinding

class OrderListRecyclerViewAdapter(
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<OrderListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = OrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(
        var binding: OrderCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData() {
            binding.orderCost.text = "₹20.00"
            binding.orderId.text = "Order ID : 23"
            binding.orderDate.text = "Ordered date" + "23/07/19"

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}