package com.example.neostore_android.views


import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neostore_android.NeoStoreApplication
import com.example.neostore_android.R
import com.example.neostore_android.adapters.MyCartListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyCartPageBinding
import com.example.neostore_android.databinding.QuantityDialogBoxBinding
import com.example.neostore_android.models.CartProduct
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.utils.toPriceFormat
import com.example.neostore_android.viewmodels.MyCartPageViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MyCartPage : BaseFragment<FragmentMyCartPageBinding>() {

    private val model: MyCartPageViewModel by viewModels {
        MyCartPageViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,
            (requireActivity().application as NeoStoreApplication).cartRepository
        )

    }

    override fun setUpViews() {
        binding.orderNowButton.setOnClickListener {
            findNavController().navigate(R.id.action_myCartPage_to_addressListPage)
        }
    }

    override fun observeData() {
        model.cart.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    visibleLoadingScreen(View.VISIBLE)
                    visibleErrorScreen(View.GONE)
                    binding.content.visibility = View.GONE
                }
                is NetworkData.Success ->
                    state.data?.let {
                        if (it.cartProduct.isNullOrEmpty()) {
                            visibleLoadingScreen(View.GONE)
                            visibleErrorScreen(View.GONE)
                            binding.content.visibility = View.GONE
                            binding.emptyList.visibility = View.VISIBLE
                        } else {
                            setRecyclerView(it.cartProduct.toMutableList())
                            binding.cartTotal.text = getTotalPrice(it.cartProduct)
                            visibleLoadingScreen(View.GONE)
                            visibleErrorScreen(View.GONE)
                            binding.content.visibility = View.VISIBLE
                            binding.emptyList.visibility = View.GONE
                        }
                    }
                is NetworkData.Error -> {
                    visibleLoadingScreen(View.GONE)
                    visibleErrorScreen(View.VISIBLE)
                    binding.content.visibility = View.GONE
                    binding.errorScreen.errorText.text =
                        state.error?.userMsg ?: state.error?.message
                                ?: getString(R.string.error_occurred)
                    binding.errorScreen.retryButton.setOnClickListener {
                        model.getCartItems()
                    }
                }
            }
        })
    }

    private fun getTotalPrice(list: List<CartProduct>): String {
        var price: Long = 0
        for (data in list) {
            price += data.cartProductItem.subTotal
        }
        return "₹${price.toString().toPriceFormat()}"
    }


    private fun setRecyclerView(data: MutableList<CartProduct>) {
        binding.myCartListsRecyclerView.adapter =
            MyCartListRecyclerViewAdapter(data) { index ->
                createDialogBoxForQuantity(data[index]) { quantity ->
                    data[index].quantity = quantity
                    data[index].cartProductItem.subTotal =
                        quantity * data[index].cartProductItem.cost
                    binding.myCartListsRecyclerView.adapter?.notifyItemChanged(index)
                    binding.cartTotal.text = getTotalPrice(data)
                }
            }
        val itemTouchHelper =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    onDismiss(data[position].productID)
                    data.removeAt(position)
                    binding.myCartListsRecyclerView.adapter?.notifyItemRemoved(position)
                    binding.cartTotal.text = getTotalPrice(data)
                    if (data.size < 1) {
                        binding.content.visibility=View.GONE
                        binding.emptyList.visibility = View.VISIBLE
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    RecyclerViewSwipeDecorator.Builder(
                        context,
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                        .addSwipeLeftActionIcon(R.drawable.delete).setIconHorizontalMargin(15)
                        .create()
                        .decorate()
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )

                }

            }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.myCartListsRecyclerView)
    }


    private fun onDismiss(productID: Number) {
        model.deleteCartItem(productID).observe(
            viewLifecycleOwner, { state ->
                when (state) {
                    is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                    is NetworkData.Success -> {
                        visibleLoadingScreen(View.GONE)
                        showSnackBar(
                            state.data?.userMsg ?: state.data?.message
                            ?: getString(R.string.success)
                        )

                    }
                    is NetworkData.Error -> {
                        visibleLoadingScreen(View.GONE)
                        showSnackBar(
                            state.error?.userMsg ?: state.error?.message
                            ?: getString(R.string.error_occurred)
                        )
                        model.getCartItems()
                    }
                }

            }
        )
    }

    private fun createDialogBoxForQuantity(product: CartProduct, onChange: (Long) -> Unit) {
        val bindingDialog = QuantityDialogBoxBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setView(bindingDialog.root)
        val builder = dialog.show()
        bindingDialog.apply {
            productTitleDialogBox.text = product.cartProductItem.name
            Glide.with(binding.root.context).load(product.cartProductItem.productImages)
                .error(R.drawable.ic_launcher_background)
                .into(productImageDialogBox)
            quantitySubmitButton.setOnClickListener {
                if (Validation.validateEmptyInput(bindingDialog.quantityEditText)) {
                    val quantity = bindingDialog.quantityEditText.editText?.text.toString()
                    model.editCartItem(product.cartProductItem.id, quantity.toInt())
                        .observe(viewLifecycleOwner, { state ->
                            when (state) {
                                is NetworkData.Loading -> visibleLoadingScreen(View.VISIBLE)
                                is NetworkData.Error -> {
                                    visibleLoadingScreen(View.GONE)
                                    showSnackBar(
                                        state.error?.userMsg ?: state.error?.message
                                        ?: getString(R.string.error_occurred)
                                    )
                                }
                                is NetworkData.Success -> {
                                    visibleLoadingScreen(View.GONE)
                                    showSnackBar(
                                        state.data?.userMsg ?: state.data?.message
                                        ?: getString(R.string.success)
                                    )
                                    onChange(quantity.toLong())
                                }
                            }
                        })
                    builder.dismiss()
                }
            }

        }

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
    ): FragmentMyCartPageBinding = FragmentMyCartPageBinding.inflate(inflater, container, false)


}