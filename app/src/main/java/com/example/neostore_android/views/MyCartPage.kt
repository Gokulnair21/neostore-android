package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.neostore_android.adapters.MyCartListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyCartPageBinding

class MyCartPage : BaseFragment<FragmentMyCartPageBinding>() {


    override fun setUpViews() {
        binding.myCartListsRecyclerView.adapter = MyCartListRecyclerViewAdapter()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyCartPageBinding = FragmentMyCartPageBinding.inflate(inflater, container, false)


}