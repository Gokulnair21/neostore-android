package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentLoginPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.LoginPageViewModel


class LoginPage : BaseFragment<FragmentLoginPageBinding>() {


    private val model: LoginPageViewModel by viewModels()


    override fun setUpViews() {
        binding.loginButton.setOnClickListener {
            formValidate()
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginPage_to_registerPage)
        }
    }

    private fun formValidate() {
        if (Validation.validateEmptyInput(binding.usernameTextInput) && Validation.validateEmptyInput(
                binding.passwordTextInput
            )
        ) {
            model.login(
                binding.usernameTextInput.editText?.text.toString(),
                binding.usernameTextInput.editText?.text.toString()
            ).observe(
                viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> Toast.makeText(
                            context,
                            "Loading",
                            Toast.LENGTH_SHORT
                        ).show()
                        is NetworkData.Success -> Toast.makeText(
                            context,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        is NetworkData.Error -> Toast.makeText(
                            context,
                            state.error?.userMsg,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }
            )
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginPageBinding = FragmentLoginPageBinding.inflate(inflater, container, false)


}
