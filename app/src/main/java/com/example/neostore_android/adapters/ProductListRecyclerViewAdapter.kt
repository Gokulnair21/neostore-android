package com.example.neostore_android.adapters


import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.ProductCardBinding
import com.example.neostore_android.models.ProductList
import com.example.neostore_android.utils.toPriceFormat

class ProductListRecyclerViewAdapter(
    private val currentProductList: MutableList<ProductList>,
    private val allProductList: List<ProductList>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ProductListRecyclerViewAdapter.ViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProductCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(currentProductList[position])

    }

    override fun getItemCount(): Int = currentProductList.size

    inner class ViewHolder(
        var binding: ProductCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bindData(product: ProductList) {
            binding.price.text = "Rs. ${product.cost.toString().toPriceFormat()}"
            binding.productTitle.text = product.name.replaceFirstChar {
                it.uppercase()
            }
            binding.producer.text = product.producer.replaceFirstChar {
                it.uppercase()
            }
            binding.productRating.rating = product.rating.toFloat()
            Glide.with(binding.root.context).load(product.productImage)
                .error(R.drawable.ic_launcher_background)
                .into(binding.productThumbnail)

        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }


    override fun getFilter(): Filter = productListFilter

    private val productListFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredProductList: MutableList<ProductList> = mutableListOf()
            constraint?.let {
                if (it.toString().isEmpty()) {
                    filteredProductList.addAll(allProductList)
                } else {
                    for (product in allProductList) {
                        if (product.name.lowercase().contains(it.toString().lowercase())) {
                            filteredProductList.add(product)
                        }
                    }
                }
            }
            val filterResults = FilterResults()
            filterResults.values = filteredProductList
            return filterResults

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            results?.let {
                currentProductList.clear()
                currentProductList.addAll(it.values as MutableList<ProductList>)
                notifyDataSetChanged()
            }


        }

    }
}