package com.example.neostore_android

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.neostore_android.databinding.ActivityMainBinding
import com.example.neostore_android.viewmodels.AccountSharedViewModel
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.neostore_android.repositories.PreferenceRepository
import com.example.neostore_android.utils.NetworkData
import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout


class MainActivity : AppCompatActivity(), View.OnClickListener, DrawerLayout.DrawerListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val preferenceRepository: PreferenceRepository by lazy {
        (application as NeoStoreApplication).preferenceRepository
    }

    private val model: AccountSharedViewModel by viewModels {
        AccountSharedViewModel.Factory(preferenceRepository.accessToken)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.drawerLayout.setDrawerListener(this)
        binding.mainAppBar.setupWithNavController(navController)
        setDrawerListener()
        drawerDetails()
    }

    private fun setDrawerListener() {
        val drawerDesignBinding = binding.drawerDesign
        drawerDesignBinding.myCartPage.setOnClickListener(this)
        drawerDesignBinding.tableProductListPage.setOnClickListener(this)
        drawerDesignBinding.sofaProductListPage.setOnClickListener(this)
        drawerDesignBinding.chairProductListPage.setOnClickListener(this)
        drawerDesignBinding.cupboardProductListPage.setOnClickListener(this)
        drawerDesignBinding.myAccountPage.setOnClickListener(this)
        drawerDesignBinding.storeLocatorPage.setOnClickListener(this)
        drawerDesignBinding.myOrdersPage.setOnClickListener(this)
        drawerDesignBinding.logOut.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun drawerDetails() {
        val drawerDesignBinding = binding.drawerDesign
        model.account.observe(this, { state ->
            when (state) {
                is NetworkData.Loading -> {
                    binding.mainAppBar.setNavigationOnClickListener(null)
                    binding.drawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                is NetworkData.Success -> {
                    state.data?.account?.let {
                        drawerDesignBinding.myCartProductCount.text = it.totalCarts.toString()
                        drawerDesignBinding.emailID.text = it.user.email
                        drawerDesignBinding.name.text = "${it.user.firstName} ${it.user.lastName}"
                        Glide.with(this)
                            .load(it.user.profilePic ?: "").centerCrop()
                            .placeholder(R.drawable.user_male)
                            .into(drawerDesignBinding.profilePic)
                    }
                    binding.mainAppBar.setNavigationOnClickListener {
                        binding.drawerLayout.openDrawer()
                    }
                    binding.drawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_UNLOCKED)

                }
                is NetworkData.Error -> {
                    binding.mainAppBar.setNavigationOnClickListener(null)
                    binding.drawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
            }
        })
    }

    private val listener =
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            if (destination.id == R.id.homePage) {
                binding.mainAppBar.visibility = View.VISIBLE
                binding.mainAppBar.setNavigationOnClickListener {
                    binding.drawerLayout.openDrawer()
                }
                binding.mainAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.menu_icon)
                binding.drawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_UNLOCKED)

            } else {
                binding.mainAppBar.visibility = View.VISIBLE
                binding.mainAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.chevron_left_24)
                binding.mainAppBar.setNavigationOnClickListener {
                    navController.navigateUp()
                }
                binding.drawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED)
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


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.myCartPage -> navController.navigate(R.id.myCartPage)
            R.id.tableProductListPage -> navController.navigate(R.id.tableProductListPage)
            R.id.sofaProductListPage -> navController.navigate(R.id.sofaProductListPage)
            R.id.chairProductListPage -> navController.navigate(R.id.chairProductListPage)
            R.id.cupboardProductListPage -> navController.navigate(R.id.cupboardProductListPage)
            R.id.myAccountPage -> navController.navigate(R.id.myAccountPage)
            R.id.storeLocatorPage -> navController.navigate(R.id.storeLocatorPage)
            R.id.myOrdersPage -> navController.navigate(R.id.myOrdersPage)
            R.id.logOut -> {
                if (preferenceRepository.setAccessToken(null)) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast(getString(R.string.error_occurred))
                }
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerOpened(drawerView: View) {
        binding.mainAppBar.navigationIcon =
            AppCompatResources.getDrawable(this, R.drawable.chevron_left_24)
    }

    override fun onDrawerClosed(drawerView: View) {
        if (navController.currentDestination?.id == R.id.homePage) {
            binding.mainAppBar.navigationIcon =
                AppCompatResources.getDrawable(this, R.drawable.menu_icon)
        }
    }

    override fun onDrawerStateChanged(newState: Int) {
    }
}