package com.example.neostore_android.views


import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.databinding.FragmentAddAddressPageBinding
import com.example.neostore_android.models.Address
import com.example.neostore_android.repositories.AddressRepository
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.AddressListPageViewModel
import com.google.android.material.snackbar.Snackbar

class AddAddressPage : BaseFragment<FragmentAddAddressPageBinding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddAddressPageBinding =
        FragmentAddAddressPageBinding.inflate(inflater, container, false)

    private val model: AddressListPageViewModel by activityViewModels()

    override fun setUpViews() {
        binding.saveAddressButton.setOnClickListener {
            saveAddress()
        }
    }

    private fun saveAddress() {
        if (Validation.validateEmptyInput(binding.addressTypeTextInput) && Validation.validateEmptyInput(
                binding.addressTextInput
            ) && Validation.validateEmptyInput(
                binding.landmarkTextInput
            ) && Validation.validateName(binding.cityTextInput) &&
            Validation.validateName(binding.stateTextInput) && Validation.validateZipCode(binding.zipCodeTextInput) && Validation.validateName(
                binding.countryTextInput
            )
        ) {
            val address = Address(
                addressType = binding.addressTypeTextInput.editText?.text.toString(),
                address = binding.addressTextInput.editText?.text.toString(),
                landmark = binding.landmarkTextInput.editText?.text.toString(),
                city = binding.cityTextInput.editText?.text.toString(),
                zipCode = binding.zipCodeTextInput.editText?.text.toString(),
                country = binding.countryTextInput.editText?.text.toString(),
                state = binding.stateTextInput.editText?.text.toString()
            )
            model.insert(address)
            findNavController().navigateUp()
            showSnackBar("Address added")
        }
    }


}