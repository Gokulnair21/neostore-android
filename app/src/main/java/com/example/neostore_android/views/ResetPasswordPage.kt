package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.neostore_android.databinding.FragmentResetPasswordPageBinding
import com.example.neostore_android.utils.Validation

class ResetPasswordPage : BaseFragment<FragmentResetPasswordPageBinding>() {


    override fun setUpViews() {
        binding.resetPasswordButton.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        if (Validation.validateEmptyInput(binding.currentPasswordTextInput) && Validation.validatePassword(
                binding.newPasswordTextInput
            ) && Validation.validateConfirmPassword(
                binding.newPasswordTextInput,
                binding.confirmPasswordTextInput
            )
        ) {
            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResetPasswordPageBinding =
        FragmentResetPasswordPageBinding.inflate(inflater, container, false)

}