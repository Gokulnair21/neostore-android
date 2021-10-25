package com.example.neostore_android.views


import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.neostore_android.R
import com.example.neostore_android.adapters.MyCartListRecyclerViewAdapter
import com.example.neostore_android.databinding.FragmentMyCartPageBinding
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MyCartPage : BaseFragment<FragmentMyCartPageBinding>() {


    override fun setUpViews() {
        binding.myCartListsRecyclerView.adapter = MyCartListRecyclerViewAdapter()
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.myCartListsRecyclerView)
    }

    private var itemTouchHelper =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                snackBar()
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

    private fun snackBar() {

        Snackbar.make(requireView(), "Removed from the cart", Snackbar.LENGTH_SHORT)
            .show()
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyCartPageBinding = FragmentMyCartPageBinding.inflate(inflater, container, false)


}