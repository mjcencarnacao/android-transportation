package com.mjceo.transportation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.gson.Gson
import com.mjceo.transportation.R
import com.mjceo.transportation.activities.MainActivity
import com.mjceo.transportation.databinding.FragmentLoginBinding
import com.mjceo.transportation.utils.SharedPreferencesManager.customPrefs
import com.mjceo.transportation.utils.Utilities
import com.mjceo.transportation.utils.Utilities.loadCurrentUser
import com.mjceo.transportation.utils.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val TAG = javaClass.simpleName
    private var auth = FirebaseAuth.getInstance()
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        binding.ivStartAccount.setOnClickListener { lifecycleScope.launch(Dispatchers.IO) { login() } }
        binding.tvLoginRegister.setOnClickListener { findNavController().navigate(R.id.login_to_register) }
    }

    private suspend fun login() {
        val user = User(binding.etLoginEmail.text.toString(), binding.etLoginPassword.text.toString())
        if (user.email.isNotBlank() && user.password.isNotBlank()) {
            auth.signInWithEmailAndPassword(user.email, user.password).await()
            auth.currentUser?.let {
                loadCurrentUser(requireContext())
                startActivity(Intent(context, MainActivity::class.java)).also { activity?.finish() }
            } ?: throw FirebaseAuthException(TAG, "Failed to Authenticate to Firebase.")
        }
    }
}