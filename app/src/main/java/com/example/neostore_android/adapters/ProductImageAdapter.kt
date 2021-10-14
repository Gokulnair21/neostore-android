package com.example.neostore_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.ProductImageCardBinding
import com.example.neostore_android.models.ProductImage


class ProductImageAdapter(
    private val values: List<ProductImage>, private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ProductImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ProductImageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(values[position])

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(
        var binding: ProductImageCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(product: ProductImage) {
            Glide.with(binding.root.context).load(product.image).centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(binding.productImage)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}