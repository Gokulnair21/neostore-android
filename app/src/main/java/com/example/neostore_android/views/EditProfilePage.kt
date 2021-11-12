package com.example.neostore_android.views


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.text.InputType
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentEditProfilePageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.utils.toEditable
import com.example.neostore_android.viewmodels.AccountSharedViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


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
                                        .load(selected)
                                        .placeholder(R.drawable.user_male)
                                        .into(binding.profilePicture)
                                    imagePath =
                                        "data:" + requireActivity().contentResolver.getType(selected) + ";base64," + imagePath
                                }
                            } catch (e: Exception) {
                                showToast(getString(R.string.error_occurred))
                            }
                        }
                    }
                }
            }//else photo not picked statements
        }

    override fun setUpViews() {
        binding.dateOfBirthTextInput.editText?.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            isFocusable = false
            setOnClickListener { showDateTimePicker() }
        }
        binding.submitButton.setOnClickListener {
            submit()
        }
        binding.profilePictureCard.setOnClickListener {
            checkPermission()
        }
    }

    private fun showDateTimePicker() {
        try {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_birthdate))
                    .setCalendarConstraints(setupConstraintsBuilder().build()).build()
            datePicker.apply {
                addOnPositiveButtonClickListener {
                    val date = Date(it)
                    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                    binding.dateOfBirthTextInput.editText?.text = (sdf.format(date)).toEditable()
                }
            }
            fragmentManager?.let { datePicker.show(it, "DatePicker") }
        } catch (e: Exception) {
            showSnackBar(getString(R.string.error_occurred))
        }
    }

    private fun checkPermission() {
        when {
            (ContextCompat.checkSelfPermission(
                binding.root.context,
                externalStoragePermission
            ) == PackageManager.PERMISSION_GRANTED) -> getPhoto()
            shouldShowRequestPermissionRationale(externalStoragePermission) -> {
                showToast(getString(R.string.permanent_image_rejection))
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
                    phoneNumber = binding.phoneNumberTextInput.editText?.text.toString(),
                    dob = binding.dateOfBirthTextInput.editText?.text.toString()
                ).observe(viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                        is NetworkData.Success -> {
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: getString(R.string.success)
                            )
                            visibleLoadingScreen(View.GONE)
                            model.getAccountDetails()
                            findNavController().navigateUp()
                        }
                        is NetworkData.Error -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.error?.userMsg ?: state.error?.message
                                ?: getString(R.string.error_occurred)
                            )

                        }

                    }

                })
            }
        } else {
            showToast(getString(R.string.no_image_selected))
        }
    }

    private fun setupConstraintsBuilder(): CalendarConstraints.Builder {
        val constraintsBuilder = CalendarConstraints.Builder()
        val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        c[1960, 1] = 1
        val cEnd = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val start = c.timeInMillis
        val end = cEnd.timeInMillis
        constraintsBuilder.setStart(start)
        constraintsBuilder.setEnd(end)
        constraintsBuilder.setOpenAt(end)
        return constraintsBuilder
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