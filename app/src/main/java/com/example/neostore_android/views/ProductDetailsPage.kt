package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductImageAdapter
import com.example.neostore_android.databinding.FragmentProductDetailsPageBinding
import com.example.neostore_android.databinding.FragmentProductListPageBinding
import com.example.neostore_android.models.Product
import com.example.neostore_android.models.ProductImage
import com.example.neostore_android.models.ProductRatingResponse
import com.example.neostore_android.models.ProductResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData
import kotlin.math.cos


class ProductDetailsPage : Fragment() {

    private var _binding: FragmentProductDetailsPageBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProductDetailsPageArgs>()
    private val model: ProductDetailsPageViewModel by viewModels<ProductDetailsPageViewModel> {
        ProductDetailsPageViewModel.Factory(args.productID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsPageBinding.inflate(inflater, container, false)
        model.product.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {

                }
                is NetworkData.Success -> {
                    state.data?.let {
                        attachElements(it.data)
                    }
                }
                is NetworkData.Error -> {

                }
            }
        })

        return binding.root
    }


    private fun attachElements(product: Product) {
        product.apply {
            binding.outOfStockLabel.text = ""
            binding.productName.text = name
            binding.productDescription.text = description
            binding.productPrice.text = "Rs. $cost"
            binding.productCategory.text = "Table"
            binding.productProducer.text = producer
            setProductImage(productImages)
            setMainImage(productImages[0])


        }
        binding.buyNowButton.setOnClickListener {
            Toast.makeText(context, "CLicked me", Toast.LENGTH_SHORT).show()
        }
        binding.rateButton.setOnClickListener {

        }


    }


    private fun setProductImage(images: List<ProductImage>) {
        binding.productImageRecyclerView.adapter = ProductImageAdapter(images) {
            setMainImage(images[it])
        }
    }


    private fun setMainImage(image: ProductImage) {
        Glide.with(binding.root.context).load(image.image)
            .error(R.drawable.ic_launcher_background)
            .into(binding.mainProductImage)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

class ProductDetailsPageViewModel(private val productID: String) : ViewModel() {


    private val productRepository = ProductRepository()

    var product = MutableLiveData<NetworkData<ProductResponse>>()
    var productRating = MutableLiveData<NetworkData<ProductRatingResponse>>()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        product = productRepository.getProductDetails(productID)
    }

    fun setProductRating(rating: Number) {
        productRating = productRepository.setProductRating(productID, rating)
    }

    class Factory(private val productID: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProductDetailsPageViewModel(productID) as T
        }
    }
}
