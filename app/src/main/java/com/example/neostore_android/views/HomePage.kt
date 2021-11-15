package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentHomePageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.AccountSharedViewModel
import com.synnapps.carouselview.ImageListener


class HomePage : BaseFragment<FragmentHomePageBinding>() {

    private val imageArray = intArrayOf(
        R.drawable.slider_img1,
        R.drawable.slider_img2,
        R.drawable.slider_img3,
        R.drawable.slider_img4
    )
    private val imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(imageArray[position]) }

    private val model: AccountSharedViewModel by activityViewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomePageBinding = FragmentHomePageBinding.inflate(inflater, container, false)

    override fun setUpViews() {
        binding.carousel.setImageListener(imageListener)
        binding.carousel.pageCount = imageArray.size

        binding.sofasButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_sofaProductListPage)
        }
        binding.chairButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_chairProductListPage)
        }
        binding.tableButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_tableProductListPage)
        }
        binding.cupboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePage_to_cupboardProductListPage)
        }
    }

    override fun observeData() {
        model.account.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    visibleLoadingScreen(View.VISIBLE)
                    binding.content.visibility=View.GONE
                    visibleErrorScreen(View.GONE)
                }
                is NetworkData.Success -> {
                    visibleLoadingScreen(View.GONE)
                    binding.content.visibility=View.VISIBLE
                    visibleErrorScreen(View.GONE)
                }
                is NetworkData.Error -> {
                    visibleLoadingScreen(View.GONE)
                    binding.content.visibility=View.GONE
                    visibleErrorScreen(View.VISIBLE)
                    binding.errorScreen.apply {
                        errorText.text=getString(R.string.error_occurred)
                        retryButton.setOnClickListener { model.getAccountDetails() }
                    }
                }
            }
        })
    }


    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }

    private fun visibleErrorScreen(status: Int) {
        binding.errorScreen.errorScreenLayout.visibility = status
    }


}


