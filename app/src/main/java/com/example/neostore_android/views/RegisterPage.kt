package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.neostore_android.databinding.FragmentRegisterPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.RegisterPageViewModel

class RegisterPage : BaseFragment<FragmentRegisterPageBinding>() {

    private val model: RegisterPageViewModel by viewModels()


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

            })
        }
    }


    private fun validateGender(): Boolean {
        if (binding.genderRadioGroup.checkedRadioButtonId == -1) {
            Toast.makeText(context, "Please select a gender", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateCheckBox(): Boolean {
        val checked = binding.agreeConditionsCheckBox.isChecked
        if (!checked) {
            Toast.makeText(context, "Please check the conditions", Toast.LENGTH_SHORT).show()
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


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterPageBinding = FragmentRegisterPageBinding.inflate(inflater, container, false)
}