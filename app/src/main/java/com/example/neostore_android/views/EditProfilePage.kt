package com.example.neostore_android.views


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.neostore_android.databinding.FragmentEditProfilePageBinding
import com.example.neostore_android.utils.Validation


class EditProfilePage : BaseFragment<FragmentEditProfilePageBinding>() {

    private val externalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE

    private var activityResultLauncherPermission: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                getPhoto()
            }
        }

    private var activityResultLauncherForImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.data != null) {
                    result.data?.let { intentData ->
                        intentData.data?.let { selected ->
                            try {
                                val inputStream =
                                    requireActivity().contentResolver.openInputStream(selected)
                                binding.profilePicture.setImageBitmap(
                                    BitmapFactory.decodeStream(
                                        inputStream
                                    )
                                )
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Error occurred,Please try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }//else photo not picked statements
        }

    override fun setUpViews() {
        binding.submitButton.setOnClickListener {
            submit()
        }
        binding.profilePictureCard.setOnClickListener {
            checkPermission()
        }
    }


    private fun checkPermission() {
        when {
            (ContextCompat.checkSelfPermission(
                binding.root.context,
                externalStoragePermission
            ) == PackageManager.PERMISSION_GRANTED) -> getPhoto()
            shouldShowRequestPermissionRationale(externalStoragePermission) -> {
                Toast.makeText(context, "Permanent Rejection", Toast.LENGTH_SHORT).show()
            }
            else -> activityResultLauncherPermission.launch(externalStoragePermission)
        }
    }

    private fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncherForImage.launch(intent)
    }

    private fun submit() {
        if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(
                binding.lastNameTextInput
            ) && Validation.validateEmail(
                binding.emailTextInput
            ) && Validation.validateMobileNumber(binding.phoneNumberTextInput)
        ) {
            Toast.makeText(context, "Validated", Toast.LENGTH_SHORT).show()
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfilePageBinding =
        FragmentEditProfilePageBinding.inflate(inflater, container, false)

}