package com.example.neostore_android.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neostore_android.databinding.FragmentRegisterPageBinding

class RegisterPage : Fragment() {
    private var _binding: FragmentRegisterPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterPageBinding.inflate(inflater, container, false)


        binding.registerButton.setOnClickListener {
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}