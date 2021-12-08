package com.studywithme.app.present.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentRoomListBinding
import com.studywithme.app.objects.room.Room
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.RoomRecyclerViewAdapter
import com.studywithme.app.present.adapters.RoomRecyclerViewAdapter.OnRoomClickListener
import com.studywithme.app.present.models.RoomListViewModel

@Suppress("Detekt.TooManyFunctions")
class RoomListFragment :
    Fragment(),
    OnRoomClickListener,
    RoomListViewModel.InternetCheck,
    SearchView.OnQueryTextListener {

    private val viewModel = RoomListViewModel(this)
    private var _binding: FragmentRoomListBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter = RoomRecyclerViewAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.fragment_rooms_title)

        viewModel.findRooms("")

        observeModel()

        binding.roomList.adapter = recyclerAdapter

        binding.swipeContainer.setOnRefreshListener {
            viewModel.findRooms("")
            binding.swipeContainer.isRefreshing = false
        }
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                openFragment(CreateRoomFragment())
            }
            R.id.action_search -> {
                (item.actionView as SearchView).setOnQueryTextListener(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

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
                    recyclerAdapter.update(it.data.map { it as Room })
                    recyclerAdapter.values.clear()
                    recyclerAdapter.values.addAll(it.data.map { it as Room })
                    Toast.makeText(requireContext(), "Success: ${it.data.size}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRoomClick(position: Long) {
        Toast.makeText(context, "Room $position clicked", Toast.LENGTH_SHORT).show()
        openFragment(ChatFragment.newInstance(position))
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

    override fun isOnline(): Boolean {
        var isOnline = false
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when (true) {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isOnline = true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isOnline = true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> isOnline = true
            }
        }
        return isOnline
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.findRooms(query)
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.findRooms(newText)
        return false
    }
}
