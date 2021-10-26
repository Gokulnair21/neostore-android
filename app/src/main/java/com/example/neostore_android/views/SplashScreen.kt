package com.example.neostore_android.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.databinding.FragmentSplashScreenBinding
import com.example.neostore_android.repositories.PreferenceRepository
import com.example.neostore_android.viewmodels.SplashScreenViewModel

class SplashScreen : BaseFragment<FragmentSplashScreenBinding>() {

    private val model: SplashScreenViewModel by viewModels {
        SplashScreenViewModel.Factory(PreferenceRepository(requireActivity()))
    }


    override fun observeData() {
        model.data.observe(viewLifecycleOwner, {
            if (it != 0) {
                if (it % 2 == 0) {
                    findNavController().navigate(R.id.action_splashScreen_to_homePage)
                } else {
                    findNavController().navigate(R.id.action_splashScreen_to_homePage)
                }
            }
        })

    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashScreenBinding =
        FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

}