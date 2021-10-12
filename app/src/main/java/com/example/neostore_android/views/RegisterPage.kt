package com.example.neostore_android.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.neostore_android.databinding.FragmentRegisterPageBinding
import com.example.neostore_android.utils.Validation

class RegisterPage : Fragment() {
    private var _binding: FragmentRegisterPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterPageBinding.inflate(inflater, container, false)


        binding.registerButton.setOnClickListener {
            register()
        }

        return binding.root
    }

    private fun register() {
        if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(binding.lastNameTextInput) && Validation.validateEmail(
                binding.emailTextInput
            ) && Validation.validatePassword(binding.passwordTextInput) && Validation.validateConfirmPassword(
                binding.passwordTextInput,
                binding.confirmPasswordTextInput
            ) && validateGender() && Validation.validateMobileNumber(binding.phoneNumberTextInput) && validateCheckBox()
        ) {

            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
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
        val checked=binding.agreeConditionsCheckBox.isChecked
         if(!checked){
             Toast.makeText(context,"Please check the conditions",Toast.LENGTH_SHORT).show()
         }
        return checked

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}