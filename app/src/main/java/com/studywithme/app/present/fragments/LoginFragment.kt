package com.studywithme.app.present.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.studywithme.app.databinding.FragmentLoginBinding
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.activity.MainActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        auth = Firebase.auth
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}