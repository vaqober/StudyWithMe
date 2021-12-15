package com.studywithme.app.present.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.studywithme.app.R
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.databinding.FragmentChatBinding
import com.studywithme.app.objects.message.Message
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.ChatRecyclerViewAdapter
import com.studywithme.app.present.models.ChatViewModel
import java.util.*
import org.koin.android.ext.android.inject

class ChatFragment :
    Fragment(),
    SearchView.OnQueryTextListener {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val providerGlide by inject<IGlideProvider>()
    private var adapter = ChatRecyclerViewAdapter(mutableListOf(), providerGlide)
    private val viewModel = ChatViewModel()
    private var roomId: String = ""

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

        roomId = requireArguments().getString(ARG_ROOM).toString()
        viewModel.allMessages(roomId, "")
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
            viewModel.postMessage(roomId, newMessage)
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
        roomId = requireArguments().getString(ARG_ROOM).toString()
        viewModel.allMessages(roomId, query)

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    companion object {
        private const val ARG_ROOM: String = ""
        private const val dateMult = 1000

        @JvmStatic
        fun newInstance(roomId: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROOM, roomId)
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
