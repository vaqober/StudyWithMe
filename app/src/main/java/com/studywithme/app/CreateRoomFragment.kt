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
import androidx.fragment.app.Fragment
import com.studywithme.app.databinding.FragmentCreateRoomBinding

class CreateRoomFragment : Fragment() {

    private var _binding: FragmentCreateRoomBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
            val name = binding.roomNameTextField.editText?.text.toString()
            val theme = binding.roomThemeTextField.editText?.text.toString()
            val description = binding.roomDescriptionTextField.editText?.text.toString()
            val isPrivate = binding.privateSwitch.isChecked
            val room = Room("", name, theme, description, isPrivate)
            Toast.makeText(it.context, room.toString(), Toast.LENGTH_LONG).show()
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
        uri?.let { binding.addPhotoButton.icon = Drawable.createFromPath(uri.path) }
    }

    private fun setPhotoPickButtonSettings() {
        // TODO выбрать картинку с помощью интента и ActivityResultContract
        binding.addPhotoButton.setOnClickListener {
            selectImageFromGalleryResult.launch("image/*")
        }
    }
}
