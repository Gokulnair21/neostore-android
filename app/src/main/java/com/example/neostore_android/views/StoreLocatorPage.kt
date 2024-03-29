package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.neostore_android.adapters.StoreLocatorRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentStoreLocatorPageBinding


class StoreLocatorPage : BaseFragment<FragmentStoreLocatorPageBinding>() {


    override fun setUpViews() {
        binding.locationPlacesRecyclerView.adapter= StoreLocatorRecyclerViewAdapter()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoreLocatorPageBinding =
        FragmentStoreLocatorPageBinding.inflate(inflater, container, false)
}