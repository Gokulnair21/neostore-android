package com.example.neostore_android.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.adapters.OrderedItemRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentOrderDetailsPageBinding
import com.example.neostore_android.models.OrderDetail
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.toPriceFormat
import com.example.neostore_android.viewmodels.MyCartPageViewModel
import com.example.neostore_android.viewmodels.OrderDetailsPageViewModel

class OrderDetailsPage : BaseFragment<FragmentOrderDetailsPageBinding>() {

    private val args: OrderDetailsPageArgs by navArgs()

    private val model: OrderDetailsPageViewModel by viewModels {
        OrderDetailsPageViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).orderRepository,
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,
            args.orderID
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Order ID: ${args.orderID}"
    }


    override fun observeData() {
        model.orderDetail.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    visibleLoadingScreen(View.VISIBLE)
                    visibleErrorScreen(View.GONE)
                    binding.content.visibility = View.GONE
                }
                is NetworkData.Success -> state.data?.let {
                    binding.orderedItemListsRecyclerView.adapter =
                        OrderedItemRecyclerViewAdapter(it.order.orderDetails)
                    binding.orderDetailsCost.text = totalPrice(it.order.orderDetails)
                    visibleLoadingScreen(View.GONE)
                    visibleErrorScreen(View.GONE)
                    binding.content.visibility = View.VISIBLE
                }
                is NetworkData.Error -> {
                    visibleLoadingScreen(View.GONE)
                    visibleErrorScreen(View.VISIBLE)
                    binding.content.visibility = View.GONE
                    binding.errorScreen.errorText.text =
                        state.error?.userMsg ?: state.error?.message ?: "Try again"
                    binding.errorScreen.retryButton.setOnClickListener {
                        model.getOrderDetails()
                    }
                }
            }
        })
    }

    private fun totalPrice(orderDetails: List<OrderDetail>): String {
        var amount: Long = 0
        for (i in orderDetails) {
            amount += i.total
        }
        return "₹${amount.toString().toPriceFormat()}"
    }

    private fun visibleLoadingScreen(status: Int) {
        binding.loadingScreen.loadingScreenLayout.visibility = status
    }

    private fun visibleErrorScreen(status: Int) {
        binding.errorScreen.errorScreenLayout.visibility = status
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailsPageBinding =
        FragmentOrderDetailsPageBinding.inflate(inflater, container, false)

}