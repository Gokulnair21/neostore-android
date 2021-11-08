package com.example.neostore_android.views


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.neostore_android.MainActivity
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.databinding.FragmentRegisterPageBinding
import com.example.neostore_android.repositories.PreferenceRepository
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.LoginActivityViewModel

class RegisterPage : BaseFragment<FragmentRegisterPageBinding>() {

    private val model: LoginActivityViewModel by viewModels {
        LoginActivityViewModel.Factory((requireActivity().application as NeoStoreApplication).userRepository)
    }


    private val preferenceRepository: PreferenceRepository by lazy {
        (requireActivity().application as NeoStoreApplication).preferenceRepository
    }


    override fun setUpViews() {
        binding.registerButton.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(binding.lastNameTextInput) && Validation.validateEmail(
                binding.emailTextInput
            ) && Validation.validatePassword(binding.passwordTextInput) && Validation.validateConfirmPassword(
                binding.passwordTextInput,
                binding.confirmPasswordTextInput
            ) && validateGender() && Validation.validateMobileNumber(binding.phoneNumberTextInput) && validateCheckBox()
        ) {

            model.register(
                firstName = binding.firstNameTextInput.editText?.text.toString(),
                lastName = binding.lastNameTextInput.editText?.text.toString(),
                email = binding.emailTextInput.editText?.text.toString(),
                gender = getGender(binding.genderRadioGroup.checkedRadioButtonId),
                password = binding.passwordTextInput.editText?.text.toString(),
                phoneNumber = binding.phoneNumberTextInput.editText?.text.toString(),
                confirmPassword = binding.confirmPasswordTextInput.editText?.text.toString()
            ).observe(viewLifecycleOwner, { state ->
                when (state) {
                    is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                    is NetworkData.Success -> {
                        visibleLoadingScreen(View.GONE)
                        showSnackBar(
                            state.data?.userMsg ?: state.data?.message ?: "Registered Successfully"
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
                            ?: "Error occurred while removing"
                        )
                    }
                }

            })
        }
    }


    private fun validateGender(): Boolean {
        if (binding.genderRadioGroup.checkedRadioButtonId == -1) {
            showToast("Please select a gender")
            return false
        }
        return true
    }

    private fun validateCheckBox(): Boolean {
        val checked = binding.agreeConditionsCheckBox.isChecked
        if (!checked) {
            showToast("Please check the conditions")
        }
        return checked

    }

    private fun getGender(id: Int): String {
        return when (id) {
            binding.radioMale.id -> binding.radioMale.text.toString()
            binding.radioFemale.id -> binding.radioFemale.text.toString()
            else -> "Male"
        }
    }

    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPageBinding = FragmentRegisterPageBinding.inflate(inflater, container, false)
}