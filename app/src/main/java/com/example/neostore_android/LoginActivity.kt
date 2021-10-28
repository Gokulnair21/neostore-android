package com.example.neostore_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.neostore_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.loginAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.loginHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.loginAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    private val listener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginPage) {
                binding.loginAppBar.visibility = View.GONE
            } else {
                binding.loginAppBar.navigationIcon =
                    AppCompatResources.getDrawable(this, R.drawable.chevron_left_24)
                binding.loginAppBar.visibility = View.VISIBLE
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
}