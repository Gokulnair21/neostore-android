package com.example.neostore_android.views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.adapters.AddressListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentAddressListPageBinding
import com.example.neostore_android.models.Address
import com.example.neostore_android.repositories.AddressRepository
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.AddressListPageViewModel

class AddressListPage : BaseFragment<FragmentAddressListPageBinding>() {

    private val model: AddressListPageViewModel by activityViewModels {
        AddressListPageViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).addressRepository,
            (requireActivity().application as NeoStoreApplication).orderRepository,
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,

            )
    }
    private var address: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun observeData() {
        model.addresses.observe(viewLifecycleOwner, {
            binding.addressListsRecyclerView.adapter =
                AddressListRecyclerViewAdapter(it.toMutableList(), model) { index ->
                    address = it[index]
                }
        })
    }

    override fun setUpViews() {
        binding.placeOrderButton.setOnClickListener {
            if (address != null) {
                model.placeOrder(address.toString()).observe(viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Error -> showSnackBar(
                            state.error?.userMsg ?: state.error?.message ?: "Error occured"
                        )
                        is NetworkData.Success -> {
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: "Your order has been placed"
                            )
                            findNavController().navigateUp()
                            findNavController().navigateUp()
                        }
                        is NetworkData.Loading -> {
                        }
                    }

                })
            } else {
                showToast("Please select a address")
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.address_page_action_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_addressListPage_to_addAddressPage)
        return super.onOptionsItemSelected(item)
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressListPageBinding =
        FragmentAddressListPageBinding.inflate(inflater, container, false)

}