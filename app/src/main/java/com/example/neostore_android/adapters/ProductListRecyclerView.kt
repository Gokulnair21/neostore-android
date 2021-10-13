package com.example.neostore_android.adapters


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neostore_android.databinding.ProductCardBinding
import com.example.neostore_android.models.ProductList

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
            binding.price.text = "Rs. ${product.cost}"
            binding.productTitle.text = product.name
            binding.producer.text = product.producer

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}