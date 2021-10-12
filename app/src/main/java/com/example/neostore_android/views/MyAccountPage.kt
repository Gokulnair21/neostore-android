package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentMyAccountPageBinding

class MyAccountPage : Fragment() {

    private var _binding: FragmentMyAccountPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAccountPageBinding.inflate(inflater, container, false)
        binding.resetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_resetPasswordPage)
        }
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_editProfilePage)
        }
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}