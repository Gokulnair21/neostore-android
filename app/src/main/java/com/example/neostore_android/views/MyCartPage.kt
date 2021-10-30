package com.example.neostore_android.views


import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.neostore_android.models.CartProductItem
import com.example.neostore_android.models.Product
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.utils.Validation
import com.example.neostore_android.utils.toPriceFormat
import com.example.neostore_android.viewmodels.MyCartPageViewModel
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MyCartPage : BaseFragment<FragmentMyCartPageBinding>() {

    private val model: MyCartPageViewModel by viewModels {
        MyCartPageViewModel.Factory(
            (requireActivity().application as NeoStoreApplication).preferenceRepository.accessToken,
            (requireActivity().application as NeoStoreApplication).cartRepository
        )

    }

    override fun observeData() {
        model.cart.observe(viewLifecycleOwner, { state ->
            when (state) {
                is NetworkData.Success -> {
                    state.data?.let {
                        setRecyclerView(it.cartProduct.toMutableList())
                        binding.cartTotal.text =
                            "Rs.${getTotalPrice(it.cartProduct).toPriceFormat()}"
                    }
                }
                is NetworkData.Error -> {

                }
                is NetworkData.Loading -> {
                }
            }
        })
    }

    private fun getTotalPrice(list: List<CartProduct>): String {
        var price: Long = 0
        for (data in list) {
            price += data.cartProductItem.subTotal
        }
        return price.toString()
    }


    private fun setRecyclerView(data: MutableList<CartProduct>) {
        binding.myCartListsRecyclerView.adapter =
            MyCartListRecyclerViewAdapter(data) {
                createDialogBoxForQuantity(data[it])
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
                    data.removeAt(position)
                    binding.myCartListsRecyclerView.adapter?.notifyItemChanged(position)
                    model.deleteCartItem(data[position].productID).observe(
                        viewLifecycleOwner, { state ->
                            when (state) {
                                is NetworkData.Error -> snackBar(
                                    state.data?.userMsg ?: state.data?.message
                                    ?: "Error occurred while removing"
                                )
                                is NetworkData.Loading -> {
                                }
                                is NetworkData.Success -> snackBar(
                                    state.data?.userMsg ?: state.data?.message
                                    ?: "Removed from the cart"
                                )
                            }

                        }
                    )
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


    override fun setUpViews() {
        binding.orderNowButton.setOnClickListener {
            findNavController().navigate(R.id.action_myCartPage_to_addressListPage)
        }
    }

    private fun createDialogBoxForQuantity(product: CartProduct) {
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
                                is NetworkData.Loading -> {
                                }
                                is NetworkData.Error -> snackBar(
                                    state.error?.userMsg ?: "Error occured,Try again"
                                )
                                is NetworkData.Success -> {
                                    snackBar(state.data?.userMsg ?: "Success")
                                    model.getCartItems()
                                }
                            }
                        })
                    builder.dismiss()
                }
            }

        }

    }


    private fun snackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyCartPageBinding = FragmentMyCartPageBinding.inflate(inflater, container, false)


}