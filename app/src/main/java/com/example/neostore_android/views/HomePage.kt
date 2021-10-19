package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentHomePageBinding


class HomePage : Fragment() {
    private var _binding: FragmentHomePageBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().actionBar?.title = "Gokul"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomePageBinding.inflate(inflater, container, false)

        binding.sofasButton.setOnClickListener {
            val action = HomePageDirections.actionHomePageToSofaProductListPage("3")
            findNavController().navigate(action)
        }

        binding.chairButton.setOnClickListener {
            val action = HomePageDirections.actionHomePageToChairProductListPage("2")
            findNavController().navigate(action)
        }
        binding.tableButton.setOnClickListener {
            val action = HomePageDirections.actionHomePageToTableProductListPage("1")
            findNavController().navigate(action)
        }
        binding.cupboardButton.setOnClickListener {
            val action = HomePageDirections.actionHomePageToCupboardProductListPage("4")
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}