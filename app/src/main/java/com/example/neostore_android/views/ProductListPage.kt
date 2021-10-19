package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentProductListPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.ProductDetailsPageViewModel
import com.example.neostore_android.viewmodels.ProductListViewModel

class
ProductListPage : Fragment() {

    private var _binding: FragmentProductListPageBinding? = null
    private val binding get() = _binding!!

    private val model: ProductListViewModel by viewModels {
        ProductListViewModel.Factory(args.productType)
    }

    private val args: ProductListPageArgs by navArgs()

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

                    state.data?.let {
                        binding.productListRecyclerView.adapter =
                            ProductListRecyclerViewAdapter(it.data ?: listOf()) { index ->
                                val bundle = bundleOf("productID" to it.data[index].id.toString())
                                findNavController().navigate(R.id.productDetailsPage, bundle)
                            }
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