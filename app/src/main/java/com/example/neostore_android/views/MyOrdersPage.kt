package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.adapters.OrderListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyOrdersPageBinding

class MyOrdersPage : Fragment() {

    private var _binding: FragmentMyOrdersPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyOrdersPageBinding.inflate(inflater, container, false)

        binding.myOrdersListsRecyclerView.adapter = OrderListRecyclerViewAdapter() {
            val action=MyOrdersPageDirections.actionMyOrdersPageToOrderDetailsPage(it)
            findNavController().navigate(action)
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}