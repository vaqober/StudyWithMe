package com.studywithme.app.present.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studywithme.app.databinding.ActivityRegisterBinding
import com.studywithme.app.present.adapters.PagerAdapter

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        initFunc()
    }

    private fun initial() {
        binding.viewPager2.adapter = PagerAdapter(this)
        binding.tabLayout.tabIconTint = null
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {
                tab, position ->
            when (position) {
                0 -> { tab.text = "Login" }
                1 -> { tab.text = "Sign Up" }
                else -> { tab.text = "Login" }
            }
        }.attach()
    }

    private fun initFunc() {
        if (auth.currentUser != null) {
            Toast.makeText(this, auth.currentUser.toString(), Toast.LENGTH_LONG).show()
            val intent = Intent(this.applicationContext, MainActivity::class.java)
            startActivity(intent)
        } else {
            initial()
        }
    }
}