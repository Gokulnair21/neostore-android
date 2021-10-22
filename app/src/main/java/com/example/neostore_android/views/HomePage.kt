package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentHomePageBinding


class HomePage : BaseFragment<FragmentHomePageBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomePageBinding = FragmentHomePageBinding.inflate(inflater, container, false)

    override fun setUpViews() {
        super.setUpViews()
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


}