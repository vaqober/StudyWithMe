package com.studywithme.app.present.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.studywithme.app.R
import com.studywithme.app.databinding.ActivityMainBinding
import com.studywithme.app.present.DrawerLocker
import com.studywithme.app.present.fragments.RoomListFragment

class MainActivity : AppCompatActivity(), DrawerLocker {

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
        transaction.replace(R.id.fragment_container, fragment)
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
}
