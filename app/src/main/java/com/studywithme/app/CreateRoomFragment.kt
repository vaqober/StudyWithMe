package com.studywithme.app

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
import com.studywithme.app.databinding.FragmentCreateRoomBinding
import com.studywithme.app.models.Room
import kotlinx.coroutines.launch

class CreateRoomFragment : Fragment() {

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

        return binding.root
    }

    private fun setCreateButtonSettings() {
        binding.createButton.setOnClickListener {
            val photo = imageUri.toString()
            val name = binding.roomNameTextField.editText?.text.toString()
            val theme = binding.roomThemeTextField.editText?.text.toString()
            val description = binding.roomDescriptionTextField.editText?.text.toString()
            val isPrivate = binding.privateSwitch.isChecked
            val room = Room(photo, name, theme, description, isPrivate)

            if (name == "") {
                Toast.makeText(context, "Missing name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                MockDataStore.postRoom(room)
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
            binding.addPhotoButton.setImageDrawable(
                Drawable.createFromStream(inputStream, uri.toString())
            )
            binding.addPhotoButton.setPadding(0)
            imageUri = uri
        }
    }

    private fun setPhotoPickButtonSettings() {
        binding.addPhotoButton.setOnClickListener {
            selectImageFromGalleryResult.launch("image/*")
        }
    }
}
