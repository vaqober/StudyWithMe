package com.studywithme.app.present.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.studywithme.app.R
import com.studywithme.app.databinding.ActivityMainBinding
import com.studywithme.app.databinding.NavProfileHeaderBinding
import com.studywithme.app.present.DrawerLocker
import com.studywithme.app.present.State
import com.studywithme.app.present.fragments.RegisterFragment
import com.studywithme.app.present.fragments.RoomListFragment
import com.studywithme.app.present.models.UserProfileViewModel

class MainActivity :
    AppCompatActivity(),
    DrawerLocker,
    NavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val viewModel by viewModels<UserProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        setSupportActionBar(binding.toolbar.root)
        if (auth.currentUser != null) {
            Toast.makeText(
                this,
                "main Activity" + auth.currentUser?.email,
                Toast.LENGTH_LONG
            ).show()
            setUserInfo(auth)
            menuSettings()
        }

        val fragment = RegisterFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, fragment.javaClass.toString())
        transaction.commitAllowingStateLoss()
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
            R.id.nav_logout -> {
                auth.signOut()
                fragment = RegisterFragment()
                fragName = fragment.javaClass.toString()
            }
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
                transaction.commitAllowingStateLoss()
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

    private fun setUserInfo(auth: FirebaseAuth) {
        val id = "5"
        viewModel.getUser(id)
        observeModel()
    }

    @SuppressLint("SetTextI18n")
    private fun observeModel() {
        viewModel.getState().observe(this) { it ->
            when (it) {
                is State.Pending -> {
                }
                is State.Fail -> {
                    Toast.makeText(this, "Fail: ${it.error}", Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    Toast.makeText(
                        this,
                        "viewModel.getUser " + it.data.getName(),
                        Toast.LENGTH_LONG
                    ).show()
                    val photoUri = it.data.getPhotoUri()
                    val b = NavProfileHeaderBinding.inflate(layoutInflater)
                    b.navProfileHeaderUsername.text = it.data.getName()
                    Glide.with(this)
                        .load(photoUri)
                        .into(b.navProfileHeaderUserPhoto)

                    Toast.makeText(this, "Success: ${it.data}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
