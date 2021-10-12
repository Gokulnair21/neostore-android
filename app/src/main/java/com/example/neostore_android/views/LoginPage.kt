package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentLoginPageBinding
import com.example.neostore_android.utils.Validation


class LoginPage : Fragment() {
    private var _binding: FragmentLoginPageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginPageBinding.inflate(inflater, container, false)


        binding.loginButton.setOnClickListener {
            formValidate()
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginPage_to_registerPage)
        }
        return binding.root
    }


    private fun formValidate() {
        if (Validation.validateEmptyInput(binding.usernameTextInput) && Validation.validateEmptyInput(
                binding.passwordTextInput
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
