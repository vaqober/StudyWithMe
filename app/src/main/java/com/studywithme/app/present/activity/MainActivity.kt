package com.studywithme.app.present.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.studywithme.app.R
import com.studywithme.app.databinding.ActivityMainBinding
import com.studywithme.app.present.DrawerLocker
import com.studywithme.app.present.fragments.RoomListFragment

class MainActivity :
    AppCompatActivity(),
    DrawerLocker,
    NavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.root)
        menuSettings()

        val fragment = RoomListFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, fragment.javaClass.toString())
        transaction.commit()
    }

    private fun menuSettings() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            binding.toolbar.root,
            R.string.nav_open_drawer,
            R.string.nav_close_drawer
        )
        binding.drawer.addDrawerListener(toggle)
        binding.navMenu.setNavigationItemSelectedListener(this)
        toggle.syncState()
    }

    override fun setDrawerLocked(shouldLock: Boolean) {
        if (shouldLock) {
            binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            supportActionBar?.hide()
        } else {
            binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            supportActionBar?.show()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.getItemId()
        var fragment: Fragment? = null
        var fragName = ""

        when (id) {
            R.id.nav_rooms -> {
                fragment = RoomListFragment()
                fragName = fragment.javaClass.toString()
            }
            // R.id.nav_settings -> {}
            // R.id.nav_logout -> {}
        }

        if (fragment != null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val parentFrag = supportFragmentManager.findFragmentByTag(fragName)
            val lastFrag = supportFragmentManager.fragments.last()

            if (parentFrag != null && lastFrag == parentFrag) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                transaction.hide(supportFragmentManager.fragments.last())
                transaction.add(R.id.fragment_container, fragment, fragName)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else {
            Toast.makeText(this, "Soon", Toast.LENGTH_SHORT).show()
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        Log.i("FragmentStackBefore", supportFragmentManager.fragments.toString())
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
        Log.i("FragmentStackAfter", supportFragmentManager.fragments.toString())
    }
}
