package com.ddevs.linkit.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import com.ddevs.linkit.R
import com.ddevs.linkit.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment.navController
        )
       handleIntent(intent)
    }
    private fun handleIntent(intent: Intent): Boolean {
        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type) {
            val textToShare = intent.getStringExtra(Intent.EXTRA_TEXT)
            Log.d("Intent",intent.toString())
            Log.d("Intent",intent.extras.toString())
            Log.d("Intent Data",intent.data.toString())
            intent.dataString?.let { Log.d("Intent Data String", it) }
            // The intent comes from Direct share
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
                intent.hasExtra(Intent.EXTRA_SHORTCUT_ID)) {
                val shortcutId = intent.getStringExtra(Intent.EXTRA_SHORTCUT_ID)
                if(shortcutId=="Add"){
                    navHostFragment.findNavController().navigate(R.id.addLinkFragment)
                }
                else{
                    navHostFragment.findNavController().navigate(R.id.addLinkFragment)
                }
            } else {
                navHostFragment.findNavController().navigate(R.id.addLinkFragment)
            }
            return true
        }
        return false
    }
}