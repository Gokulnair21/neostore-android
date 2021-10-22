package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.adapters.OrderListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyOrdersPageBinding

class MyOrdersPage : BaseFragment<FragmentMyOrdersPageBinding>() {


    override fun setUpViews() {
        binding.myOrdersListsRecyclerView.adapter = OrderListRecyclerViewAdapter() {
            val action = MyOrdersPageDirections.actionMyOrdersPageToOrderDetailsPage(it)
            findNavController().navigate(action)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersPageBinding = FragmentMyOrdersPageBinding.inflate(inflater, container, false)
}