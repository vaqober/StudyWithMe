package com.studywithme.app.present.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.postDelayed
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentRoomListBinding
import com.studywithme.app.objects.room.Room
import com.studywithme.app.present.State
import com.studywithme.app.present.adapters.FilterRecyclerViewAdapter
import com.studywithme.app.present.adapters.FilterRecyclerViewAdapter.OnFilterClickListener
import com.studywithme.app.present.adapters.RoomRecyclerViewAdapter
import com.studywithme.app.present.adapters.RoomRecyclerViewAdapter.OnRoomClickListener
import com.studywithme.app.present.models.RoomListViewModel

class RoomListFragment : Fragment(), OnFilterClickListener, OnRoomClickListener {
    private val viewModel by viewModels<RoomListViewModel>()
    private var _binding: FragmentRoomListBinding? = null
    private val binding get() = _binding!!
    private val recyclerAdapter = RoomRecyclerViewAdapter(mutableListOf(), this)
    private val filterAdapter = FilterRecyclerViewAdapter(mutableListOf(), this)
    private val handler = Handler(Looper.getMainLooper())

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
        viewModel.findAll()
        observeModel()
        setAdapters()
        setOnClickListeners()
        setSearchListeners(view)
    }

    private fun setAdapters() {
        // filterList.addAll(listOf("Math", "Bath", "Kek", "Pek"))
        binding.filterList.adapter = filterAdapter
        binding.roomList.adapter = recyclerAdapter
    }

    private fun setSearchListeners(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    v.performClick()
                    viewModel.findAll()
                }
            }
            true
        }
        binding.menuAutocomplete.doAfterTextChanged { input ->
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(delayInMillis = 600) {
                recyclerAdapter.update(recyclerAdapter.values.filter { it.getTitle().contains(input.toString())})
            }
        }
    }

    private fun setOnClickListeners() {
        binding.fabCreate.setOnClickListener {
            openFragment(CreateRoomFragment())
        }
        val themes = resources.getStringArray(R.array.hints_for_theme).toList()
        val adapter = ArrayAdapter(binding.root.context, R.layout.them_list_item, themes)
        binding.menuAutocomplete.setAdapter(adapter)
        binding.menuAutocomplete.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Выводим выпадающий список
                binding.menuAutocomplete.showDropDown()
            }
        }
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
                    recyclerAdapter.values.clear()
                    recyclerAdapter.values.addAll(it.data.map { it as Room })
                    recyclerAdapter.update(it.data.map { it as Room })
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

    override fun onFilterClick(position: Int) {
        Toast.makeText(context, "Filter $position clicked", Toast.LENGTH_SHORT).show()
        filterAdapter.values.removeAt(position)
        filterAdapter.notifyItemRemoved(position)
    }

    override fun onRoomClick(position: Int) {
        Toast.makeText(context, "Room $position clicked", Toast.LENGTH_SHORT).show()
        openFragment(MembersFragment())
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
