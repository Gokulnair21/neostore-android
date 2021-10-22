package com.example.neostore_android.views



import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.neostore_android.databinding.FragmentEditProfilePageBinding
import com.example.neostore_android.utils.Validation


class EditProfilePage : BaseFragment<FragmentEditProfilePageBinding>() {

    override fun setUpViews() {
        binding.submitButton.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(binding.lastNameTextInput) && Validation.validateEmail(
                binding.emailTextInput
            ) && Validation.validateMobileNumber(binding.phoneNumberTextInput)
        ) {
            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfilePageBinding =
        FragmentEditProfilePageBinding.inflate(inflater, container, false)

}