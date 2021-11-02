package com.example.neostore_android.views


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentEditProfilePageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.viewmodels.AccountSharedViewModel


class EditProfilePage : BaseFragment<FragmentEditProfilePageBinding>() {

    private val model: AccountSharedViewModel by activityViewModels()

    private val externalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
    private var imagePath: String = ""


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
                                inputStream?.let {
                                    val sourceBytes = inputStream.readBytes()
                                    imagePath = Base64.encodeToString(sourceBytes, Base64.DEFAULT)
                                    Glide.with(requireContext())
                                        .load(Base64.decode(imagePath, Base64.DEFAULT))
                                        .placeholder(R.drawable.user_male)
                                        .into(binding.profilePicture)
                                }
                            } catch (e: Exception) {
                                showToast("Error occurred,Please try again")
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
                showToast("Hey,you have permanently rejected this app in accessing your storage")
            }
            else -> activityResultLauncherPermission.launch(externalStoragePermission)
        }
    }

    private fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activityResultLauncherForImage.launch(intent)
    }

    private fun submit() {
        if (imagePath.isNotEmpty()) {
            if (Validation.validateName(binding.firstNameTextInput) && Validation.validateName(
                    binding.lastNameTextInput
                ) && Validation.validateEmail(
                    binding.emailTextInput
                ) && Validation.validateMobileNumber(binding.phoneNumberTextInput)
            ) {
                model.updateAccountDetails(
                    firstName = binding.firstNameTextInput.editText?.text.toString(),
                    lastName = binding.lastNameTextInput.editText?.text.toString(),
                    email = binding.emailTextInput.editText?.text.toString(),
                    profilePic = imagePath,
                    phoneNumber = binding.phoneNumberTextInput.editText?.text.toString()
                ).observe(viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                        is NetworkData.Success -> {
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message ?: "Edited successfully"
                            )
                            visibleLoadingScreen(View.GONE)
                            model.getAccountDetails()
                            findNavController().navigateUp()
                        }
                        is NetworkData.Error -> {
                            showSnackBar(
                                state.error?.userMsg ?: state.error?.message
                                ?: "Error occurred,Try again."
                            )

                        }

                    }

                })
            }
        } else {
            showToast("No image selected.Please select a image")
        }
    }


    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditProfilePageBinding =
        FragmentEditProfilePageBinding.inflate(inflater, container, false)

}