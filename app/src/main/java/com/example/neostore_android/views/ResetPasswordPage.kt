package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.databinding.FragmentResetPasswordPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.AccountSharedViewModel

class ResetPasswordPage : BaseFragment<FragmentResetPasswordPageBinding>() {

    private val model: AccountSharedViewModel by activityViewModels()

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
            model.changePassword(
                binding.currentPasswordTextInput.editText?.text.toString(),
                binding.newPasswordTextInput.editText?.text.toString(),
                binding.confirmPasswordTextInput.editText?.text.toString(),
            ).observe(viewLifecycleOwner, { state ->
                when (state) {
                    is NetworkData.Error -> {
                    }
                    is NetworkData.Loading -> {
                    }
                    is NetworkData.Success -> {
                        model.getAccountDetails()
                        findNavController().navigateUp()
                    }
                }

            })
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResetPasswordPageBinding =
        FragmentResetPasswordPageBinding.inflate(inflater, container, false)

}