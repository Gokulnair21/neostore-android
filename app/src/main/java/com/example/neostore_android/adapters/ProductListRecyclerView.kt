package com.example.neostore_android.adapters


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.ProductCardBinding
import com.example.neostore_android.models.ProductList
import com.example.neostore_android.utils.toPriceFormat

class ProductListRecyclerViewAdapter(
    private val values: List<ProductList>, private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(values[position])

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(
        var binding: ProductCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(product: ProductList) {
            binding.price.text = "Rs. ${product.cost.toString().toPriceFormat()}"
            binding.productTitle.text = product.name.replaceFirstChar {
                it.uppercase()
            }
            binding.producer.text = product.producer.replaceFirstChar {
                it.uppercase()
            }
            Glide.with(binding.root.context).load(product.productImage)
                .error(R.drawable.ic_launcher_background)
                .into(binding.productThumbnail)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}