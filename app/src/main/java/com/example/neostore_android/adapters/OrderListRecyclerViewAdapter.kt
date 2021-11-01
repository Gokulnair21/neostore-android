package com.example.neostore_android.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.databinding.OrderCardBinding
import com.example.neostore_android.models.OrderList
import com.example.neostore_android.utils.toPriceFormat

class OrderListRecyclerViewAdapter(
    private val orders: List<OrderList>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<OrderListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = OrderCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    inner class ViewHolder(
        var binding: OrderCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindData(order: OrderList) {
            binding.orderCost.text = "₹${order.cost.toInt().toString().toPriceFormat()}"
            binding.orderId.text = "Order ID : ${order.id}"
            binding.orderDate.text = "Ordered date ${order.created}"

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}