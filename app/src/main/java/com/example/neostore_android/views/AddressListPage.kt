package com.example.neostore_android.views

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

        }
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.address_page_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_addressListPage_to_addAddressPage)
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}