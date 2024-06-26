package com.example.randomusers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.randomusers.databinding.ActivityMainBinding
import com.example.randomusers.fragments.DetailsUserFragment
import com.example.randomusers.fragments.list.UsersListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DetailsUserFragment.OnDetailsUserFragment {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment
    private var currentMenuProvider: MenuProvider? = null

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var connectivityManager: ConnectivityManager
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            coroutineScope.launch {
                binding.textViewMessageNetwork.visibility = View.GONE
            }
        }

        override fun onLost(network: Network) {
            coroutineScope.launch {
                binding.textViewMessageNetwork.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavHost()
        initChangedListener()

    }

    override fun onResume() {
        super.onResume()

        initConnectionManager()
        checkNetworkStatus()
    }

    override fun onPause() {
        super.onPause()

        unregisterConnectionManager()
    }

    private fun initNavHost() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.usersListFragment -> {
                    initToolbar(getString(R.string.titleUsers))
                    currentMenuProvider?.let {
                        removeMenuProvider(it)
                    }
                    initToolBarMenu(R.menu.menu_users_list)
                }

                R.id.detailsUserFragment -> {
                    currentMenuProvider?.let {
                        removeMenuProvider(it)
                    }
                }
            }
        }
    }

    private fun initConnectionManager() {
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun unregisterConnectionManager() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun checkNetworkStatus() {
        val currentNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)

        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            binding.textViewMessageNetwork.visibility = View.GONE
        } else {
            binding.textViewMessageNetwork.visibility = View.VISIBLE
        }
    }

    private fun initToolbar(title: String, isBackBtn: Boolean = false) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = title
        if (isBackBtn) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbar.setNavigationOnClickListener {
                supportFragmentManager.popBackStack()
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun initToolBarMenu(menuFragment: Int) {
        currentMenuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuFragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                val currentFragment = navHostFragment.childFragmentManager.fragments[0]
                return when (menuItem.itemId) {
                    R.id.actionUpdateList -> {
                        if (currentFragment is UsersListFragment) {
                            currentFragment.updateList()
                        }
                        true
                    }

                    R.id.actionSortByMaxAge -> {
                        if (currentFragment is UsersListFragment) {
                            currentFragment.sortUsersByAge(true)
                        }
                        true
                    }

                    R.id.actionSortByMinAge -> {
                        if (currentFragment is UsersListFragment) {
                            currentFragment.sortUsersByAge(false)
                        }
                        true
                    }

                    else -> false
                }
            }
        }
        addMenuProvider(currentMenuProvider!!, this, Lifecycle.State.RESUMED)
    }

    override fun onInitToolBar(title: String, isBackBtn: Boolean) {
        initToolbar(title, isBackBtn)
    }
}