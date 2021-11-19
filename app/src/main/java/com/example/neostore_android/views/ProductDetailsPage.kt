package com.example.neostore_android.views


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductImageAdapter
import com.example.neostore_android.databinding.FragmentProductDetailsPageBinding
import com.example.neostore_android.databinding.QuantityDialogBoxBinding
import com.example.neostore_android.databinding.RatingDialogBoxBinding
import com.example.neostore_android.models.Product
import com.example.neostore_android.models.ProductImage
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.utils.toPriceFormat
import com.example.neostore_android.viewmodels.ProductDetailsPageViewModel


class ProductDetailsPage : BaseFragment<FragmentProductDetailsPageBinding>() {


    private val model: ProductDetailsPageViewModel by viewModels {
        ProductDetailsPageViewModel.Factory(
            requireArguments().getString("productID", "1"),
            (requireActivity().application as NeoStoreApplication).cartRepository,
            (requireActivity().application as NeoStoreApplication).productRepository,
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,
        )
    }

    private var currentImageIndex: Int = 0

    override fun observeData() {
        model.product.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    visibleLoadingScreen(View.VISIBLE)
                    binding.productDetailsLayout.visibility = View.GONE
                    visibleErrorScreen(View.GONE)
                }
                is NetworkData.Success -> {
                    state.data?.let {
                        attachElements(it.data)
                    }
                }
                is NetworkData.Error -> {
                    visibleErrorScreen(View.VISIBLE)
                    binding.productDetailsLayout.visibility = View.GONE
                    visibleLoadingScreen(View.GONE)
                    binding.errorScreen.errorText.text = getString(R.string.error_occurred)
                    binding.errorScreen.retryButton.setOnClickListener {
                        model.getProductDetails()
                    }


                }
            }
        })


    }

    private fun attachElements(product: Product) {
        product.apply {
            binding.outOfStockLabel.text = ""
            binding.productName.text = name.replaceFirstChar {
                it.uppercase()
            }
            binding.productDescription.text = description
            binding.productPrice.text = "Rs. ${cost.toString().toPriceFormat()}"
            binding.productCategory.text = getProductType(productCategoryID.toInt())
            binding.productProducer.text = producer.replaceFirstChar {
                it.uppercase()
            }
            binding.productRating.rating = rating.toFloat()
            setProductImage(productImages)
            setImageUsingGlide(binding.mainProductImage, productImages[currentImageIndex])
            binding.shareButton.setOnClickListener { shareProduct(name + "\n" + description) }
        }
        binding.buyNowButton.setOnClickListener {
            createDialogBoxForQuantity(product)
        }
        binding.rateButton.setOnClickListener {
            createDialogBoxForRating(product)
        }
        binding.productDetailsLayout.visibility = View.VISIBLE
        visibleLoadingScreen(View.GONE)
        visibleErrorScreen(View.GONE)


    }


    private fun setProductImage(images: List<ProductImage>) {
        images[currentImageIndex].isSelected = true
        binding.productImageRecyclerView.adapter = ProductImageAdapter(images) {
            if (it != currentImageIndex) {
                images[currentImageIndex].isSelected = false
                binding.productImageRecyclerView.adapter?.notifyItemChanged(currentImageIndex)
                currentImageIndex = it
                images[currentImageIndex].isSelected = true
                binding.productImageRecyclerView.adapter?.notifyItemChanged(currentImageIndex)
                setImageUsingGlide(binding.mainProductImage, images[currentImageIndex])
            }
        }
    }


    private fun createDialogBoxForQuantity(product: Product) {
        val bindingDialog = QuantityDialogBoxBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(bindingDialog.root)
        val builder = dialog.show()
        bindingDialog.apply {
            productTitleDialogBox.text = product.name
            setImageUsingGlide(productImageDialogBox, product.productImages[currentImageIndex])
            quantitySubmitButton.setOnClickListener {
                if (Validation.validateEmptyInput(quantityEditText)) {
                    val quantity = bindingDialog.quantityEditText.editText?.text.toString()
                    model.addToCart(quantity.toInt())
                        .observe(viewLifecycleOwner, { state ->
                            when (state) {
                                is NetworkData.Loading -> {
                                    visibleLoadingScreen(View.VISIBLE)
                                }
                                is NetworkData.Error -> {
                                    visibleLoadingScreen(View.GONE)
                                    showSnackBar(
                                        state.error?.userMsg ?: getString(R.string.error_occurred)
                                    )

                                }
                                is NetworkData.Success -> {
                                    visibleLoadingScreen(View.GONE)
                                    showSnackBar(state.data?.userMsg ?: getString(R.string.success))
                                }

                            }
                        })
                    builder.dismiss()
                }

            }
        }

    }

    private fun createDialogBoxForRating(product: Product) {
        val bindingDialog = RatingDialogBoxBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(bindingDialog.root)
        val builder = dialog.show()

        bindingDialog.apply {
            productTitleDialogBox.text = product.name
            setImageUsingGlide(productImageDialogBox, product.productImages[currentImageIndex])
            productRating.rating = product.rating.toFloat()
            rateNowDialogButton.setOnClickListener {
                builder.dismiss()
                model.setProductRating(productRating.rating)
                    .observe(viewLifecycleOwner, { state ->
                        when (state) {
                            is NetworkData.Loading -> {
                                visibleLoadingScreen(View.VISIBLE)
                            }
                            is NetworkData.Error -> {
                                visibleLoadingScreen(View.GONE)
                                showSnackBar(
                                    state.error?.userMsg ?: getString(R.string.error_occurred)
                                )

                            }
                            is NetworkData.Success -> {
                                visibleLoadingScreen(View.GONE)
                                showSnackBar(state.data?.userMsg ?: getString(R.string.success))
                            }

                        }
                    })
            }

        }
    }


    private fun setImageUsingGlide(imageView: ImageView, image: ProductImage) {
        Glide.with(binding.root.context).load(image.image)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
    }


    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }

    private fun visibleErrorScreen(status: Int) {
        binding.errorScreen.errorScreenLayout.visibility = status
    }

    private fun shareProduct(shareMessage: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Product Details")
            putExtra(Intent.EXTRA_TEXT, shareMessage)
        }
        startActivity(Intent.createChooser(intent, "Share using"))

    }


    private fun getProductType(productCategoryID: Int): String {
        return when (productCategoryID) {
            1 -> "Table"
            2 -> "Chair"
            3 -> "Sofa"
            4 -> "Cupboard"
            else -> "Not known"
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailsPageBinding =
        FragmentProductDetailsPageBinding.inflate(inflater, container, false)

}
