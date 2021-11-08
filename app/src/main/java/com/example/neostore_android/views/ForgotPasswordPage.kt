package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.databinding.FragmentForgotPasswordPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.LoginActivityViewModel


class ForgotPasswordPage : BaseFragment<FragmentForgotPasswordPageBinding>() {


    private val model: LoginActivityViewModel by viewModels {
        LoginActivityViewModel.Factory((requireActivity().application as NeoStoreApplication).userRepository)
    }

    override fun setUpViews() {
        binding.submitButton.setOnClickListener {
            formValidate()
        }

    }

    private fun formValidate() {
        if (Validation.validateEmptyInput(binding.usernameTextInput)
        ) {
            model.forgotPassword(
                binding.usernameTextInput.editText?.text.toString(),
            ).observe(
                viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                        is NetworkData.Success -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: "Email has been sent to you with new password"
                            )
                            findNavController().navigateUp()
                        }
                        is NetworkData.Error -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: "Error occurred.Please try again."
                            )
                        }
                    }

                }
            )
        }
    }


    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgotPasswordPageBinding =
        FragmentForgotPasswordPageBinding.inflate(inflater, container, false)

}