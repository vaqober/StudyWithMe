package com.studywithme.app.present.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.studywithme.app.present.fragments.LoginFragment
import com.studywithme.app.present.fragments.SignUpFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> SignUpFragment()
            else -> LoginFragment()
        }
    }
}
