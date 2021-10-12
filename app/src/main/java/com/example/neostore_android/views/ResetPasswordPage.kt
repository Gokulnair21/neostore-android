package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.neostore_android.databinding.FragmentResetPasswordPageBinding
import com.example.neostore_android.utils.Validation

class ResetPasswordPage : Fragment() {

    private var _binding: FragmentResetPasswordPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordPageBinding.inflate(inflater, container, false)
        binding.resetPasswordButton.setOnClickListener {
            resetPassword()
        }
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}