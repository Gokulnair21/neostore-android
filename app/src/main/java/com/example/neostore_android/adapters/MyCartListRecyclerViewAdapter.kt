package com.example.neostore_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.databinding.MyCartItemCardBinding

class MyCartListRecyclerViewAdapter() :
    RecyclerView.Adapter<MyCartListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            MyCartItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()

    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(var binding: MyCartItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData() {
//            binding.orderItemTitle.text="Test"
//            binding.orderItemType.text="("+"Tavle"+")"
//            binding.orderItemQuantity.text="QTY : "+"1"
//            binding.orderItemCost.text="₹20.00"

        }


    }
}