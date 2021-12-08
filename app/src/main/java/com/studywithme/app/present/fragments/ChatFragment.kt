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
import com.studywithme.app.databinding.FragmentChatBinding
import com.studywithme.app.objects.message.Message
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.ChatRecyclerViewAdapter
import com.studywithme.app.present.models.ChatViewModel
import java.util.Date

class ChatFragment :
    Fragment(),
    SearchView.OnQueryTextListener,
    ChatViewModel.InternetCheck {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private var adapter = ChatRecyclerViewAdapter(mutableListOf())
    private val viewModel = ChatViewModel(this)
    private var roomId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(layoutInflater)

        roomId = requireArguments().getLong(ARG_ROOM)
        viewModel.allMessages(roomId.toInt(), "")
        observeModel()
        binding.chatRecycler.adapter = adapter

        setOnClickListeners()
        return binding.root
    }

    fun setOnClickListeners() {
        binding.sendButton.setOnClickListener {
            if (binding.messageField.editText?.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Empty message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val messageText = binding.messageField.editText?.text.toString()
            binding.messageField.editText?.text?.clear()
            val newMessage = Message("Some name", messageText, Date().time / dateMult)
            viewModel.postMessage(roomId.toInt(), newMessage)
            adapter.setMessage(newMessage)
            binding.chatRecycler.smoothScrollToPosition(adapter.itemCount)
        }
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_chat, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
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
                    val messages = it.data.map { it as Message }.sortedBy { it.getDate() }
                    adapter.update(messages)
                    adapter.values.clear()
                    adapter.values.addAll(messages)
                    binding.chatRecycler.smoothScrollToPosition(adapter.itemCount)
                    Toast.makeText(requireContext(), "Success: ${it.data.size}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                openFragment(MembersFragment.newInstance(roomId))
            }
            R.id.action_search -> {
                (item.actionView as SearchView).setOnQueryTextListener(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        roomId = requireArguments().getLong(ARG_ROOM)
        viewModel.allMessages(roomId.toInt(), query)

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
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

    companion object {
        private const val ARG_ROOM: String = ""
        private const val dateMult = 1000

        @JvmStatic
        fun newInstance(roomId: Long) =
            ChatFragment().apply {
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
