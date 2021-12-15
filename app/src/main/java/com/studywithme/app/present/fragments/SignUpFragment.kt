package com.studywithme.app.present.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studywithme.app.databinding.FragmentSignUpBinding
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.activity.MainActivity
import com.studywithme.app.present.models.UsersListViewModel

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private val userViewModel by viewModels<UsersListViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        binding.signUpBtn.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val password = binding.signUpPassword.text.toString()
            val cPassword = binding.signUpConfirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && password == cPassword) {
                auth.createUserWithEmailAndPassword(email, password).addOnFailureListener {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    createUser(auth)
                    Toast.makeText(requireContext(), "acc created", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (email.isEmpty()) Toast.makeText(
                        requireContext(),
                        "Enter email, please",
                        Toast.LENGTH_SHORT
                ).show()
                else if (password.isEmpty()) Toast.makeText(
                        requireContext(),
                        "Enter password, please",
                        Toast.LENGTH_SHORT
                ).show()
                else if (password != cPassword) Toast.makeText(
                        requireContext(),
                        "Passwords do not match",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUser(auth: FirebaseAuth) {
        val id = auth.currentUser?.uid ?: ""
        val name = auth.currentUser?.email ?: ""
        val photoUri = "http://placeimg.com/640/480"
        val desc = ""
        val rooms = mutableListOf<String>()
        val isOnline = true
        val newUser = User(id, name, photoUri, desc, rooms, isOnline)
        Toast.makeText(requireContext(), name, Toast.LENGTH_LONG).show()
        userViewModel.putUser(newUser)
    }
}
