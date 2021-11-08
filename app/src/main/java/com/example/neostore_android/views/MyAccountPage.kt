package com.example.neostore_android.views


import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentMyAccountPageBinding
import com.example.neostore_android.models.User
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.toEditable
import com.example.neostore_android.viewmodels.AccountSharedViewModel

class MyAccountPage : BaseFragment<FragmentMyAccountPageBinding>() {


    private val model: AccountSharedViewModel by activityViewModels()


    override fun setUpViews() {
        binding.resetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_resetPasswordPage)
        }
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_editProfilePage)
        }
    }

    override fun observeData() {
        model.account.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {

                }
                is NetworkData.Success -> {
                    state.data?.let {
                        setViews(it.account.user)
                    }
                }
                is NetworkData.Error -> {

                }
            }
        })
    }

    private fun setViews(user: User) {
        binding.dateOfBirthTextInput.editText?.text = user.dob.toString().toEditable()
        binding.emailTextInput.editText?.text = user.email.toEditable()
        binding.firstNameTextInput.editText?.text = user.firstName.toEditable()
        binding.lastNameTextInput.editText?.text = user.lastName.toEditable()
        binding.phoneNumberTextInput.editText?.text = user.phoneNo.toString().toEditable()
        Glide.with(requireContext())
            .load(Base64.decode(user.profilePic?:"", Base64.DEFAULT))
            .placeholder(R.drawable.user_male)
            .into(binding.profilePicture)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyAccountPageBinding =
        FragmentMyAccountPageBinding.inflate(inflater, container, false)
}