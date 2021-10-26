package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentHomePageBinding
import com.synnapps.carouselview.ImageListener


class HomePage : BaseFragment<FragmentHomePageBinding>() {

    private val imageArray = intArrayOf(
        R.drawable.slider_img1,
        R.drawable.slider_img2,
        R.drawable.slider_img3,
        R.drawable.slider_img4
    )


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


    var imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(imageArray[position]) }

}


