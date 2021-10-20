package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentAddAddressPageBinding
import com.example.neostore_android.models.Address
import com.example.neostore_android.repositories.AddressRepository
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.AddressListPageViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.time.Duration
import java.util.regex.Pattern

class AddAddressPage : Fragment() {

    private var _binding: FragmentAddAddressPageBinding? = null
    private val binding get() = _binding!!

    private val model: AddressListPageViewModel by activityViewModels {
        AddressListPageViewModel.Factory(AddressRepository(requireContext()))
    }


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
            val address = Address(
                address = binding.addressTextInput.editText?.text.toString(),
                landmark = binding.landmarkTextInput.editText?.text.toString(),
                city = binding.cityTextInput.editText?.text.toString(),
                zipCode = binding.zipCodeTextInput.editText?.text.toString(),
                country = binding.countryTextInput.editText?.text.toString(),
                state = binding.stateTextInput.editText?.text.toString()
            )
            model.insert(address)
            findNavController().navigateUp()
            Snackbar.make(requireView(), "Address added", Snackbar.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}