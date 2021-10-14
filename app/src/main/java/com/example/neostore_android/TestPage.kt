package com.example.neostore_android

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.neostore_android.databinding.FragmentTestPageBinding
import com.example.neostore_android.models.ProductListResponse
import com.example.neostore_android.models.ProductResponse
import com.example.neostore_android.repositories.ProductRepository
import com.example.neostore_android.utils.NetworkData

class TestPage : Fragment() {

    private val repository: ProductRepository = ProductRepository()
    private lateinit var producatData: MutableLiveData<NetworkData<ProductListResponse>>
    private lateinit var product: MutableLiveData<NetworkData<ProductResponse>>


    private var _binding: FragmentTestPageBinding? = null
    private val binding get() = _binding!!

    private val TAG = "TEST"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentTestPageBinding.inflate(inflater, container, false)
//        producatData = repository.getProducts()
//        producatData.observe(viewLifecycleOwner, {
//            when (it) {
//                is NetworkData.Loading -> binding.value.text = "Loading"
//                is NetworkData.Error -> binding.value.text = it.error?.message
//                is NetworkData.Success -> binding.value.text = it.data?.toString()
//            }
//        })
        product = repository.getProductDetails("1")
        product.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkData.Loading -> binding.value.text = "Loading"
                is NetworkData.Error -> binding.value.text = it.error?.message
                is NetworkData.Success -> binding.value.text = it.data?.toString()
            }
        })



        return binding.root
    }


}