package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neostore_android.R
import com.example.neostore_android.adapters.MyCartListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyCartPageBinding

class MyCartPage : Fragment() {

    private var _binding: FragmentMyCartPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCartPageBinding.inflate(inflater, container, false)

        binding.myCartListsRecyclerView.adapter = MyCartListRecyclerViewAdapter()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}