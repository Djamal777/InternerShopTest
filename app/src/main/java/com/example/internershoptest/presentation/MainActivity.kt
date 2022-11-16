package com.example.internershoptest.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cart.presentation.view.CartFragment
import com.example.explorer.presentation.view.HomeStoreFragment
import com.example.internershoptest.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val cartViewModel by viewModel<com.example.cart.presentation.viewmodels.CartViewModel>()
    private val homeViewModel by viewModel<com.example.explorer.presentation.viewmodel.HomeStoreViewModel>()

    private lateinit var badge: BadgeDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.internershoptest.R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavBar.setupWithNavController(navController)

        badge =
            binding.bottomNavBar.getOrCreateBadge(com.example.internershoptest.R.id.cart_nav_graph)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                "HomeStoreFragment" -> binding.bottomNavBar.visibility =
                    View.VISIBLE
                else -> binding.bottomNavBar.visibility = View.INVISIBLE
            }
        }

        observeBasketItems()
        observeBottomSheetState()
    }

    private fun observeBottomSheetState() {
        homeViewModel.isBottomSheetShown.observe(this){
            binding.bottomNavBar.isVisible = !it
        }
    }

    private fun observeBasketItems() {
        cartViewModel.basket.observe(this) {
            when (it) {
                is com.example.common.Resource.Success -> {
                    it.data?.basket?.size?.let { size ->
                        badge.isVisible = true
                        badge.number = size
                    }
                }
                else -> {
                    badge.isVisible = false
                    badge.clearNumber()
                }
            }
        }
    }
}