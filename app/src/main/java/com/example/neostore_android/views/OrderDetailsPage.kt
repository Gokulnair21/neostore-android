package com.example.neostore_android.views


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.neostore_android.adapters.OrderedItemRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentOrderDetailsPageBinding

class OrderDetailsPage : BaseFragment<FragmentOrderDetailsPageBinding>() {

    private val args: OrderDetailsPageArgs by navArgs()

    override fun setUpViews() {
        binding.orderedItemListsRecyclerView.adapter = OrderedItemRecyclerViewAdapter() {
            Toast.makeText(context, "Order id:${args.orderID} Clicked me $it", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailsPageBinding =
        FragmentOrderDetailsPageBinding.inflate(inflater, container, false)

}