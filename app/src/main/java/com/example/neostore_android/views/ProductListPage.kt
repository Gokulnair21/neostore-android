package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentProductListPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.ProductListViewModel

class ProductListPage : Fragment() {

    private var _binding: FragmentProductListPageBinding? = null
    private val binding get() = _binding!!

    private val model: ProductListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model.getProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListPageBinding.inflate(inflater, container, false)


        model.products.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {

                }
                is NetworkData.Success -> {

                    binding.productListRecyclerView.adapter = ProductListRecyclerViewAdapter(state.data?.data?: listOf()) {
                        findNavController().navigate(R.id.action_productListPage_to_productDetailsPage)
                    }
                }
                is NetworkData.Error -> {

                }
            }
        })


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}