package com.example.neostore_android

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.neostore_android.databinding.ActivityMainBinding
import com.example.neostore_android.databinding.AppDrawerHeaderBinding
import com.example.neostore_android.utils.NetworkData
import com.example.neostore_android.viewmodels.AccountSharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    private val model: AccountSharedViewModel by viewModels {
        AccountSharedViewModel.Factory((application as NeoStoreApplication).preferenceRepository.accessToken)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.mainAppBar.setupWithNavController(navController, appBarConfiguration)
        binding.drawerNavigationView.setupWithNavController(navController)
        drawerDetails()

    }

    @SuppressLint("SetTextI18n")
    private fun drawerDetails() {
        model.account.observe(this, { state ->
            if (state is NetworkData.Success) {
                val headerBinding = binding.drawerNavigationView.getHeaderView(0)
                val drawerHeaderBinding = AppDrawerHeaderBinding.bind(headerBinding)
                state.data?.account?.let {
                    drawerHeaderBinding.emailID.text = it.user.email
                    drawerHeaderBinding.name.text = "${it.user.firstName} ${it.user.lastName}"
                }
            }
        })
    }


    private val listener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.homePage) {
                binding.mainAppBar.visibility = View.VISIBLE
                binding.mainAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.menu_icon)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            } else {
                binding.mainAppBar.visibility = View.VISIBLE
                binding.mainAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.chevron_left_24)
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                if (destination.id == R.id.productDetailsPage) {
                    binding.mainAppBar.title =
                        arguments?.getString("productName", "Product Details")
                }
            }
        }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(listener)
        super.onPause()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}