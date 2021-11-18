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
    private var currentIndex: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun observeData() {
        model.addresses.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                binding.content.visibility = View.GONE
                binding.emptyList.visibility = View.VISIBLE

            } else {
                binding.emptyList.visibility = View.GONE
                binding.addressListsRecyclerView.adapter =
                    AddressListRecyclerViewAdapter(it.toMutableList(), model) { index ->
                        if (currentIndex == null) {
                            it[index].isSelected = true
                            address = it[index]
                            currentIndex = index
                            binding.addressListsRecyclerView.adapter?.notifyItemChanged(index)
                        } else {
                            it[currentIndex!!].isSelected = false
                            binding.addressListsRecyclerView.adapter?.notifyItemChanged(currentIndex!!)
                            currentIndex = index
                            it[index].isSelected = true
                            binding.addressListsRecyclerView.adapter?.notifyItemChanged(index)
                        }


                    }
                binding.content.visibility = View.VISIBLE
            }
        })
    }

    override fun setUpViews() {
        binding.placeOrderButton.setOnClickListener {
            if (address != null) {
                model.placeOrder(address.toString()).observe(viewLifecycleOwner, { state ->
                    when (state) {
                        is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                        is NetworkData.Success -> {
                            visibleLoadingScreen(View.GONE)
                            showSnackBar(
                                state.data?.userMsg ?: state.data?.message
                                ?: getString(R.string.success)
                            )
                            findNavController().navigateUp()
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
            } else {
                showToast(getString(R.string.select_a_address))
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

    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressListPageBinding =
        FragmentAddressListPageBinding.inflate(inflater, container, false)

}