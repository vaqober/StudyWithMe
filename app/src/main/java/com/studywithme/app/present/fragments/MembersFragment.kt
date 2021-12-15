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
import androidx.recyclerview.widget.LinearLayoutManager
import com.studywithme.app.R
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.databinding.FragmentMembersBinding
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.UserRecyclerViewAdapter
import com.studywithme.app.present.adapters.UserRecyclerViewAdapter.OnUserClickListener
import com.studywithme.app.present.models.MembersListViewModel
import org.koin.android.ext.android.inject

class MembersFragment : Fragment(), OnUserClickListener {

    private val viewModel by viewModels<MembersListViewModel>()
    private val providerGlide by inject<IGlideProvider>()
    private var _binding: FragmentMembersBinding? = null
    private val binding get() = _binding!!
    private val usersOnlineListAdapter = UserRecyclerViewAdapter(
        mutableListOf(),
        this,
        providerGlide
    )
    private val usersOfflineListAdapter = UserRecyclerViewAdapter(
        mutableListOf(),
        this,
        providerGlide
    )
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
        binding.usersOnlineList.layoutManager = LinearLayoutManager(this.context)
        binding.usersOfflineList.layoutManager = LinearLayoutManager(this.context)
        binding.swipeContainer.setOnRefreshListener {
            viewModel.getMembers(roomId)
            binding.swipeContainer.isRefreshing = false
        }
    }

    private fun setAdapters() {
        binding.usersOnlineList.adapter = usersOnlineListAdapter
        binding.usersOfflineList.adapter = usersOfflineListAdapter
    }

    private fun onlineAndOfflineUsers(allMembers: List<AbstractUser>) {
        usersOnlineList.clear()
        usersOfflineList.clear()
        Log.d("UsersList", "onlineAndOfflineUsers: $allMembers")
        usersOnlineList.clear()
        usersOfflineList.clear()
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
        binding.addUserButton.setOnClickListener {
            openFragment(AddUserFragment.newInstance(requireArguments().getLong(ARG_ROOM)))
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
                    usersOnlineListAdapter.update(usersOnlineList.map { it as User })
                    usersOfflineListAdapter.update(usersOfflineList.map { it as User })
                    usersOnlineListAdapter.values.clear()
                    usersOnlineListAdapter.values.addAll(usersOnlineList.map { it as User })
                    usersOfflineListAdapter.values.clear()
                    usersOfflineListAdapter.values.addAll(usersOfflineList.map { it as User })
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
        openFragment(UserProfileFragment.newInstance(usersOfflineList[position].getId().toLong()))
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
