package com.example.neostore_android.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.OrderedItemCardBinding
import com.example.neostore_android.models.OrderDetail
import com.example.neostore_android.utils.toPriceFormat


class OrderedItemRecyclerViewAdapter(
    private val orderDetails: List<OrderDetail>
) : RecyclerView.Adapter<OrderedItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            OrderedItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(orderDetails[position])

    }

    override fun getItemCount(): Int = orderDetails.size

    inner class ViewHolder(
        var binding: OrderedItemCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindData(orderDetail: OrderDetail) {
            binding.orderItemTitle.setOnClickListener {
                binding.orderItemTitle.isSelected = !binding.orderItemTitle.isSelected
            }
            binding.orderItemTitle.text = orderDetail.prodName.replaceFirstChar {
                it.uppercase()
            }
            binding.orderItemType.text = "(${orderDetail.prodCatName})"
            binding.orderItemQuantity.text = "QTY : ${orderDetail.quantity}"
            binding.orderItemCost.text = "₹${orderDetail.total.toString().toPriceFormat()}"
            Glide.with(binding.root.context).load(orderDetail.prodImage)
                .error(R.drawable.ic_launcher_background)
                .into(binding.orderedItemImage)
        }
    }
}