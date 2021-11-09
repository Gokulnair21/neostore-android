package com.example.neostore_android.views


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.MainActivity
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentLoginPageBinding
import com.example.neostore_android.repositories.PreferenceRepository
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.LoginActivityViewModel


class LoginPage : BaseFragment<FragmentLoginPageBinding>() {


    private val model: LoginActivityViewModel by viewModels {
        LoginActivityViewModel.Factory((requireActivity().application as NeoStoreApplication).userRepository)
    }


    private val preferenceRepository: PreferenceRepository by lazy {
        (requireActivity().application as NeoStoreApplication).preferenceRepository
    }


    override fun setUpViews() {
        binding.loginButton.setOnClickListener {
            formValidate()
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginPage_to_registerPage)
        }
        binding.forgotPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginPage_to_forgotPasswordPage)
        }
    }

    private fun formValidate() {
        if (Validation.validateEmptyInput(binding.usernameTextInput) && Validation.validateEmptyInput(
                binding.passwordTextInput
            )
        ) {
            model.login(
                binding.usernameTextInput.editText?.text.toString(),
                binding.passwordTextInput.editText?.text.toString()
            ).observe(
                viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                        is NetworkData.Success -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: getString(R.string.success)
                            )
                            state.data?.user?.let {
                                preferenceRepository.setAccessToken(it.accessToken)
                                val intent = Intent(activity, MainActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }
                        is NetworkData.Error -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: getString(R.string.error_occurred)
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
    ): FragmentLoginPageBinding = FragmentLoginPageBinding.inflate(inflater, container, false)


}
