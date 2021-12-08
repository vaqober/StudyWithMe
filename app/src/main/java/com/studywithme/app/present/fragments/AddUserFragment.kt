package com.studywithme.app.present.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.studywithme.app.R
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.databinding.FragmentAddUserBinding
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.UserRecyclerViewAdapter
import com.studywithme.app.present.models.MembersListViewModel
import com.studywithme.app.present.models.UsersListViewModel
import org.koin.android.ext.android.inject

class AddUserFragment : Fragment(), UserRecyclerViewAdapter.OnUserClickListener {

    private val viewModel by viewModels<MembersListViewModel>()
    private val providerGlide by inject<IGlideProvider>()
    private val userViewModel by viewModels<UsersListViewModel>()
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!
    private val usersListAdapter = UserRecyclerViewAdapter(mutableListOf(), this, providerGlide)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.fragment_members_title)
        viewModel.getAllUsers()
        observeModel()
        setAdapter()
        observeModel()

        binding.swipeContainer.setOnRefreshListener {
            viewModel.getAllUsers()
            binding.swipeContainer.isRefreshing = false
        }
    }

    private fun setAdapter() {
        binding.allUsersList.adapter = usersListAdapter
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
                    Toast.makeText(requireContext(), "Fail: ${it.error}", Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    binding.loadingProgress.isVisible = false
                    usersListAdapter.values.clear()
                    usersListAdapter.values.addAll(it.data.map { it as User })
                    usersListAdapter.notifyDataSetChanged()
                    Toast.makeText(requireContext(), "Success: ${it.data.size}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onUserClick(position: Int) {
        val rooms = usersListAdapter.values[position].getRoomsList()
        val name = usersListAdapter.values[position].getName()

        val roomId = requireArguments().getLong(ARG_ROOM)

        if (rooms.contains(roomId)) {
            Toast.makeText(requireContext(), "$name is in this room", Toast.LENGTH_SHORT).show()
        } else {
            rooms.add(roomId)
            val userId = usersListAdapter.values[position].getId()
            val photo = usersListAdapter.values[position].getPhotoUri()
            val description = usersListAdapter.values[position].getDescription()
            val isOnline = usersListAdapter.values[position].isOnline()
            val newUser = User(userId, name, photo, description, rooms, isOnline)
            userViewModel.putUser(newUser)
            Toast.makeText(requireContext(), "Success: $name was added", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARG_ROOM: String = ""

        @JvmStatic
        fun newInstance(roomId: Long) =
            AddUserFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ROOM, roomId)
                }
            }
    }
}
