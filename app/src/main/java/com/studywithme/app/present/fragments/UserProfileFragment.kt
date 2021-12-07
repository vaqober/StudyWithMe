package com.studywithme.app.present.fragments

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
import com.studywithme.app.databinding.FragmentUserProfileBinding
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import com.studywithme.app.present.models.UserProfileViewModel
import org.koin.android.ext.android.inject

class UserProfileFragment : Fragment() {

    private val viewModel by viewModels<UserProfileViewModel>()
    private val providerGlide by inject<IGlideProvider>()
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle(R.string.fragment_profile_title)
        val userId = requireArguments().getLong(ARG_USER).toInt()
        setContentVisibility(false)
        observeModel()
        viewModel.getUser(userId)
    }

    private fun observeModel() {
        viewModel.getState().observe(viewLifecycleOwner) { it ->
            when (it) {
                is State.Pending -> {
                    binding.loadingProgress.isVisible = true
                    setContentVisibility(false)
                }
                is State.Fail -> {
                    binding.loadingProgress.isVisible = false
                    setContentVisibility(true)
                    Toast.makeText(requireContext(), "Fail: ${it.error}", Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    update(it.data as User)
                    binding.loadingProgress.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Success: ${it.data.getName()}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun setContentVisibility(visibility: Boolean) {
        binding.profileName.isVisible = visibility
        binding.profilePhoto.isVisible = visibility
        binding.profileDescription.isVisible = visibility
        binding.buttonFriens.isVisible = visibility
        binding.buttonRooms.isVisible = visibility
    }

    private fun update(user: User) {
        if (user.getId().toInt() == 1) {
            binding.buttonSettings.isVisible = true
            binding.buttonSettings.isClickable = true
        } else {
            binding.buttonSettings.isVisible = false
            binding.buttonSettings.isClickable = false
        }
        binding.profileName.text = user.getName()
        binding.profileDescription.text = user.getDescription()
        providerGlide.loadImage(user.getPhotoUri(), binding.profilePhoto)
        setContentVisibility(true)
    }

    companion object {
        private const val ARG_USER: String = ""

        @JvmStatic
        fun newInstance(userId: Long) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_USER, userId)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
