package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentAddAddressPageBinding
import com.example.neostore_android.utils.Validation
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class AddAddressPage : Fragment() {

    private var _binding: FragmentAddAddressPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAddressPageBinding.inflate(inflater, container, false)
        binding.saveAddressButton.setOnClickListener {
            saveAddress()
        }
        return binding.root
    }

    private fun saveAddress() {
        if (Validation.validateEmptyInput(binding.addressTextInput) && Validation.validateEmptyInput(
                binding.landmarkTextInput
            ) && Validation.validateName(binding.cityTextInput) &&
            Validation.validateName(binding.stateTextInput) && Validation.validateZipCode(binding.zipCodeTextInput) && Validation.validateName(
                binding.countryTextInput
            )
        ) {
            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}