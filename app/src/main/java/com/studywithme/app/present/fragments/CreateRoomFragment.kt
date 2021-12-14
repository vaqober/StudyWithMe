package com.studywithme.app.present.fragments

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.studywithme.app.R
import com.studywithme.app.databinding.FragmentCreateRoomBinding
import com.studywithme.app.objects.room.Room
import com.studywithme.app.present.DrawerLocker
import com.studywithme.app.present.State
import com.studywithme.app.present.models.CreateRoomViewModel
import kotlinx.coroutines.launch

class CreateRoomFragment : Fragment() {

    private val viewModel = CreateRoomViewModel()
    private var _binding: FragmentCreateRoomBinding? = null
    private val binding get() = _binding!!
    private var imageUri = Uri.parse(
        "android.resource://com.studywithme.app/drawable/outline_add_a_photo_black_48"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateRoomBinding.inflate(layoutInflater)
        setCreateButtonSettings()
        setThemeTextFieldSettings()
        setPhotoPickButtonSettings()
        observeModel()
        (activity as DrawerLocker?)!!.setDrawerLocked(true)
        return binding.root
    }

    private fun setCreateButtonSettings() {
        binding.createButton.setOnClickListener {
            val photo = imageUri.toString()
            val title = binding.roomNameTextField.editText?.text.toString()
            val theme = binding.roomThemeTextField.editText?.text.toString()
            val description = binding.roomDescriptionTextField.editText?.text.toString()
            val isPrivate = binding.privateSwitch.isChecked
            val room = Room("", title, theme, description, isPrivate, photo)

            if (title == "") {
                Toast.makeText(context, "Missing name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                lifecycleScope.launch {
                    viewModel.postRoom(room)
                }
            }
        }
    }

    private fun setThemeTextFieldSettings() {
        val themes = resources.getStringArray(R.array.hints_for_theme).toList()
        val adapter = ArrayAdapter<String>(binding.root.context, R.layout.them_list_item, themes)
        binding.menuAutocomplete.setAdapter(adapter)

        binding.menuAutocomplete.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Выводим выпадающий список
                binding.menuAutocomplete.showDropDown()
            }
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            binding.photoImage.setImageDrawable(
                Drawable.createFromStream(inputStream, uri.toString())
            )
            binding.photoImage.setPadding(0)
            imageUri = uri
        }
    }

    private fun setPhotoPickButtonSettings() {
        binding.addPhotoButton.setOnClickListener {
            selectImageFromGalleryResult.launch("image/*")
        }
    }

    private fun observeModel() {
        viewModel.getState().observe(viewLifecycleOwner) {
            when (it) {
                is State.Fail -> {
                    Toast.makeText(requireContext(), "Fail: ${it.error}", Toast.LENGTH_LONG)
                        .show()
                }
                is State.Success -> {
                    Toast.makeText(requireContext(), "Success: ${it.data}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        (activity as DrawerLocker?)!!.setDrawerLocked(false)
    }
}
