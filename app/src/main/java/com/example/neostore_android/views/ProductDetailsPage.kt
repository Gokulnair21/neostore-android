package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.adapters.ProductImageAdapter
import com.example.neostore_android.databinding.FragmentProductDetailsPageBinding
import com.example.neostore_android.databinding.QuantityDialogBoxBinding
import com.example.neostore_android.databinding.RatingDialogBoxBinding
import com.example.neostore_android.models.Product
import com.example.neostore_android.models.ProductImage
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.ProductDetailsPageViewModel


class ProductDetailsPage : Fragment() {

    private var _binding: FragmentProductDetailsPageBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProductDetailsPageArgs>()
    private val model: ProductDetailsPageViewModel by viewModels {
        ProductDetailsPageViewModel.Factory(args.productID)
    }

    private var currentImageIndex: Int = 0

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
            setImageUsingGlide(binding.mainProductImage, productImages[currentImageIndex])
        }
        binding.buyNowButton.setOnClickListener {
            createDialogBoxForQuantity(product)
        }
        binding.rateButton.setOnClickListener {
            createDialogBoxForRating(product)
        }


    }


    private fun setProductImage(images: List<ProductImage>) {
        binding.productImageRecyclerView.adapter = ProductImageAdapter(images) {
            currentImageIndex = it
            setImageUsingGlide(binding.mainProductImage, images[currentImageIndex])
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
                val text = bindingDialog.quantityEditText.editText?.text
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                builder.dismiss()
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
            rateNowDialogButton.setOnClickListener {
                Toast.makeText(context, "Rate now", Toast.LENGTH_SHORT).show()
                builder.dismiss()
            }
        }
    }


    private fun setImageUsingGlide(imageView: ImageView, image: ProductImage) {
        Glide.with(binding.root.context).load(image.image)
            .error(R.drawable.ic_launcher_background)
            .into(imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
