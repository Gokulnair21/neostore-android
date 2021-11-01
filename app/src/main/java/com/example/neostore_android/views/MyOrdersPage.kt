package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.adapters.OrderListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyOrdersPageBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.MyOrderPageViewModel
import com.example.neostore_android.viewmodels.OrderDetailsPageViewModel

class MyOrdersPage : BaseFragment<FragmentMyOrdersPageBinding>() {


    private val model: MyOrderPageViewModel by viewModels {
        MyOrderPageViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).orderRepository,
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,
        )

    }

    override fun observeData() {
        model.orderList.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {}
                is NetworkData.Success -> state.data?.orderList?.let {
                    binding.myOrdersListsRecyclerView.adapter =
                        OrderListRecyclerViewAdapter(it) { index ->
                            val action =
                                MyOrdersPageDirections.actionMyOrdersPageToOrderDetailsPage(it[index].id.toInt())
                            findNavController().navigate(action)
                        }
                }
                is NetworkData.Error -> {}
            }

        })
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersPageBinding = FragmentMyOrdersPageBinding.inflate(inflater, container, false)
}