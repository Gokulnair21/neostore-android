package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentProductListPageBinding
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.ProductListViewModel

class ProductListPage : BaseFragment<FragmentProductListPageBinding>() {


    private val model: ProductListViewModel by viewModels {
        ProductListViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).productRepository,
            args.productType
        )
    }

    private val args: ProductListPageArgs by navArgs()


    override fun observeData() {
        model.products.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    visibleLoadingScreen(View.VISIBLE)
                    binding.productListRecyclerView.visibility = View.GONE
                    visibleErrorScreen(View.GONE)
                }
                is NetworkData.Success -> {
                    state.data?.let {
                        onSuccess(it)
                    }
                }
                is NetworkData.Error -> {
                    visibleErrorScreen(View.VISIBLE)
                    visibleLoadingScreen(View.GONE)
                    binding.productListRecyclerView.visibility = View.GONE
                    binding.errorScreen.errorText.text = getString(R.string.error_occurred)
                    binding.errorScreen.retryButton.setOnClickListener {
                        model.getProducts()
                    }

                }
            }
        })
    }


    private fun onSuccess(productListResponse: ProductListResponse) {
        binding.productListRecyclerView.adapter =
            ProductListRecyclerViewAdapter(productListResponse.data) { index ->
                val bundle = bundleOf(
                    "productID" to productListResponse.data[index].id.toString(),
                    "productName" to productListResponse.data[index].name.replaceFirstChar { name ->
                        name.uppercase()
                    })
                findNavController().navigate(R.id.productDetailsPage, bundle)
            }
        visibleErrorScreen(View.GONE)
        visibleLoadingScreen(View.GONE)
        binding.productListRecyclerView.visibility = View.VISIBLE
    }


    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }

    private fun visibleErrorScreen(status: Int) {
        binding.errorScreen.errorScreenLayout.visibility = status
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductListPageBinding =
        FragmentProductListPageBinding.inflate(inflater, container, false)

}