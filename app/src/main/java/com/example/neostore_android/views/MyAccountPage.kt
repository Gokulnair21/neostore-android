package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentMyAccountPageBinding

class MyAccountPage : BaseFragment<FragmentMyAccountPageBinding>() {


    override fun setUpViews() {
        binding.resetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_resetPasswordPage)
        }
        binding.editProfileButton.setOnClickListener {
            findNavController().navigate(R.id.action_myAccountPage_to_editProfilePage)
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyAccountPageBinding =
        FragmentMyAccountPageBinding.inflate(inflater, container, false)
}