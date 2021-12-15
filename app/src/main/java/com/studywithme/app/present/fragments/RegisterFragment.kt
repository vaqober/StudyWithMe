package com.studywithme.app.present.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentRegisterBinding
import com.studywithme.app.present.adapters.PagerAdapter

class RegisterFragment : Fragment() {

    private lateinit var _binding: FragmentRegisterBinding
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.fragment_register_title)
        auth = Firebase.auth
        initFunc()
    }

    private fun initFunc() {
        if (auth.currentUser != null) {
            Toast.makeText(requireContext(), auth.currentUser.toString(), Toast.LENGTH_LONG).show()
            openFragment(RoomListFragment())
        } else {
            initial()
        }
    }

    private fun initial() {
        binding.viewPager2.adapter = PagerAdapter(this)
        binding.tabLayout.tabIconTint = null
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Login"
                }
                1 -> {
                    tab.text = "Sign Up"
                }
                else -> {
                    tab.text = "Login"
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        if (!parentFragmentManager.fragments.contains(fragment)) {
            transaction.add(R.id.fragment_container, fragment, null)
        }
        transaction
                .hide(this)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }
}
