package com.example.neostore_android.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.R
import com.example.neostore_android.adapters.AddressListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentAddressListPageBinding
import com.example.neostore_android.repositories.AddressRepository
import com.example.neostore_android.viewmodels.AddressListPageViewModel

class AddressListPage : Fragment() {

    private var _binding: FragmentAddressListPageBinding? = null
    private val binding get() = _binding!!

    private val model: AddressListPageViewModel by activityViewModels {
        AddressListPageViewModel.Factory(AddressRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddressListPageBinding.inflate(inflater, container, false)
        model.addresses.observe(viewLifecycleOwner, {
            binding.addressListsRecyclerView.adapter =
                AddressListRecyclerViewAdapter(it.toMutableList(), model) {
                    Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                }
        })

        binding.placeOrderButton.setOnClickListener {
            findNavController().navigate(R.id.action_addressListPage_to_addAddressPage)
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}