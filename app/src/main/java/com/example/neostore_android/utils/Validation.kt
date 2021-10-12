package com.example.neostore_android.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

object Validation {


    private const val emptyField: String = "This field is required"
    private const val passwordRegexExpression: String =
        "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$"
    private const val nameRegexExpression = "^[A-Za-z]+\$"
    private const val zipCodeRegexExpression="^[1-9][0-9]{5}\$"


    fun validateEmail(textInputLayout: TextInputLayout): Boolean {
        val data: String = textInputLayout.editText?.text.toString()
        if (data.isNullOrBlank()) {
            textInputLayout.error = emptyField
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(data).matches()) {
            textInputLayout.error = "Enter a valid email ID"
            return false
        }
        textInputLayout.error = ""
        return true
    }

    fun validatePassword(textInputLayout: TextInputLayout): Boolean {
        val pattern: Pattern = Pattern.compile(passwordRegexExpression);
        val data: String = textInputLayout.editText?.text.toString()
        if (data.isNullOrBlank()) {
            textInputLayout.error = emptyField
            return false
        } else if (!pattern.matcher(data).matches()) {
            textInputLayout.error = "Enter a valid password."
            return false
        }
        textInputLayout.error = ""
        return true
    }

    fun validateName(textInputLayout: TextInputLayout): Boolean {
        val pattern: Pattern = Pattern.compile(nameRegexExpression);
        val data: String = textInputLayout.editText?.text.toString()
        if (data.isNullOrBlank()) {
            textInputLayout.error = emptyField
            return false
        } else if (!pattern.matcher(data).matches()) {
            textInputLayout.error = "Enter a valid name."
            return false
        }
        textInputLayout.error = ""
        return true
    }

    fun validateMobileNumber(textInputLayout: TextInputLayout): Boolean {
        val data: String = textInputLayout.editText?.text.toString()
        if (data.isNullOrBlank()) {
            textInputLayout.error = emptyField
            return false
        } else if (!Patterns.PHONE.matcher(data).matches()) {
            textInputLayout.error = "Enter a valid phone number."
            return false
        }
        textInputLayout.error = ""
        return true
    }


    fun validateEmptyInput(textInputLayout: TextInputLayout): Boolean {
        val data: String = textInputLayout.editText?.text.toString()
        if (data.isNullOrEmpty()) {
            textInputLayout.error = emptyField
            return false
        }
        textInputLayout.error = ""
        return true
    }

    fun validateConfirmPassword(
        passwordTextInput: TextInputLayout,
        confirmPasswordTextInput: TextInputLayout
    ): Boolean {
        val password: String = passwordTextInput.editText?.text.toString()
        val confirmPassword: String = confirmPasswordTextInput.editText?.text.toString()
        if (confirmPassword.isNullOrEmpty()) {
            confirmPasswordTextInput.error = emptyField
            return false
        }else if (confirmPassword != password) {
            confirmPasswordTextInput.error = "Both Password and Confirm password should be same"
            return false
        }
        confirmPasswordTextInput.error = ""
        return true

    }

     fun validateZipCode(textInputLayout: TextInputLayout):Boolean{
        val pattern= Pattern.compile(zipCodeRegexExpression)
        val data=textInputLayout.editText?.text.toString()
        if(data.isNullOrBlank()){
            textInputLayout.error= emptyField
            return false
        }else if(!pattern.matcher(data).matches()){
            textInputLayout.error="Enter a valid zipcode"
            return false
        }
        textInputLayout.error=""
        return  true
    }


}