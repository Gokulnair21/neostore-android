package com.example.neostore_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.databinding.AddressListCardBinding
import com.example.neostore_android.models.Address
import com.example.neostore_android.viewmodels.AddressListPageViewModel

class AddressListRecyclerViewAdapter(
    private val values: MutableList<Address>,
    private val model: AddressListPageViewModel,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<AddressListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            AddressListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(values[position])

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(
        var binding: AddressListCardBinding,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bindData(address: Address) {
            binding.heading.text = address.addressType
            binding.address.text = address.toString()
            binding.radioButton.isChecked = address.isSelected
            binding.deleteButton.setOnClickListener {
                model.delete(address)
                values.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

}