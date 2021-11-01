package com.example.neostore_android.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.MyCartItemCardBinding
import com.example.neostore_android.models.CartProduct
import com.example.neostore_android.utils.toPriceFormat

class MyCartListRecyclerViewAdapter(
    val data: MutableList<CartProduct>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<MyCartListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            MyCartItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(data[position])

    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        var binding: MyCartItemCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.orderItemQuantity.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindData(cartProduct: CartProduct) {
            cartProduct.cartProductItem.apply {
                binding.orderItemQuantity.text = "QTY : " + "${cartProduct.quantity}"
                binding.orderItemTitle.text = name
                binding.orderItemType.text = productCategory
                binding.orderItemCost.text = "₹${cost.toString().toPriceFormat()}"
                Glide.with(binding.root.context).load(productImages)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.productImage)
            }


        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }


    }
}