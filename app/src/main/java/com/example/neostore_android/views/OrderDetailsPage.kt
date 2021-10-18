package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.neostore_android.adapters.OrderedItemRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyOrdersPageBinding
import com.example.neostore_android.databinding.FragmentOrderDetailsPageBinding

class OrderDetailsPage : Fragment() {
    private var _binding: FragmentOrderDetailsPageBinding? = null
    private val binding get() = _binding!!


    private val args:OrderDetailsPageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailsPageBinding.inflate(inflater, container, false)
        binding.orderedItemListsRecyclerView.adapter = OrderedItemRecyclerViewAdapter() {
            Toast.makeText(context, "Order id:${args.orderID} Clicked me $it", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}