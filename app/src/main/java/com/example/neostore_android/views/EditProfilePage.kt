package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.neostore_android.databinding.FragmentEditProfilePageBinding
import com.example.neostore_android.utils.Validation


class EditProfilePage : Fragment() {

    private var _binding: FragmentEditProfilePageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfilePageBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            submit()
        }
        return binding.root
    }


    private fun submit() {
        if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(binding.lastNameTextInput) && Validation.validateEmail(
                binding.emailTextInput
            ) && Validation.validateMobileNumber(binding.phoneNumberTextInput)
        ) {
            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}