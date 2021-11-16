package com.studywithme.app.present.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentMembersBinding
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.UserRecyclerViewAdapter
import com.studywithme.app.present.adapters.UserRecyclerViewAdapter.OnUserClickListener
import com.studywithme.app.present.models.MembersListViewModel

class MembersFragment : Fragment(), OnUserClickListener {

    private val viewModel by viewModels<MembersListViewModel>()
    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private val usersOnlineListAdapter = UserRecyclerViewAdapter(mutableListOf(), this)
    private val usersOfflineListAdapter = UserRecyclerViewAdapter(mutableListOf(), this)
    private val usersOnlineList: MutableList<AbstractUser> = mutableListOf()
    private val usersOfflineList: MutableList<AbstractUser> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.fragment_members_title)
        val roomId = requireArguments().getLong(ARG_ROOM)
        viewModel.getMembers(roomId)
        observeModel()
        setAdapters()
        setOnClickListeners()
    }

    private fun setAdapters() {
        binding.usersOnlineList.adapter = usersOnlineListAdapter
        binding.usersOfflineList.adapter = usersOfflineListAdapter
    }

    private fun onlineAndOfflineUsers(allMembers: List<AbstractUser>) {
        Log.d("UsersList", "onlineAndOfflineUsers: $allMembers")
        if (allMembers.isNotEmpty()) {
            for (user in allMembers) {
                Log.d("UsersList", "user " + user)
                if (user.isOnline()) usersOnlineList.add(user)
                else usersOfflineList.add(user)
            }
        }
        Log.d("UsersList", "usersOfflineList end: $usersOfflineList")
    }

    private fun setOnClickListeners() {
        binding.settingsIcon.setOnClickListener {
            openFragment(RoomSettingsFragment())
        }
        binding.addUserButton.setOnClickListener {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun observeModel() {
        viewModel.getState().observe(viewLifecycleOwner) { it ->
            when (it) {
                is State.Pending -> {
                    binding.loadingProgress.isVisible = true
                }
                is State.Fail -> {
                    binding.loadingProgress.isVisible = false
                    Toast.makeText(requireContext(), "Fail: ${it.error}", Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    binding.loadingProgress.isVisible = false
                    onlineAndOfflineUsers(it.data)
                    usersOnlineListAdapter.values.clear()
                    usersOnlineListAdapter.values.addAll(it.data.map { it as User })
                    usersOfflineListAdapter.values.clear()
                    usersOfflineListAdapter.values.addAll(it.data.map { it as User })
                    binding.usersOnline.text = " " + usersOnlineList.size
                    binding.usersOffline.text = " " + usersOfflineList.size
                    Toast.makeText(requireContext(), "Success: ${it.data.size}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onUserClick(position: Int) {
        Toast.makeText(context, "User $position clicked", Toast.LENGTH_SHORT).show()
        openFragment(UserProfileFragment())
    }

    companion object {
        private const val ARG_ROOM: String = ""

        @JvmStatic
        fun newInstance(roomId: Long) =
            MembersFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ROOM, roomId)
                }
            }
    }

    private fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment, null)
            .show(fragment)
            .hide(this)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
