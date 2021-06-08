package com.mjceo.transportation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.mjceo.transportation.R
import com.mjceo.transportation.activities.MainActivity
import com.mjceo.transportation.databinding.FragmentRegisterBinding
import com.mjceo.transportation.utils.SharedPreferencesManager.customPrefs
import com.mjceo.transportation.utils.Utilities
import com.mjceo.transportation.utils.Utilities.loadCurrentUser
import com.mjceo.transportation.utils.Utilities.md5
import com.mjceo.transportation.utils.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val TAG = javaClass.simpleName
    private val auth = FirebaseAuth.getInstance()
    private lateinit var bind: FragmentRegisterBinding
    private val database = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind = FragmentRegisterBinding.bind(view)
        bind.tvLogin.setOnClickListener { findNavController().navigateUp() }
        bind.ivStartAccount.setOnClickListener { lifecycleScope.launch(Dispatchers.IO) {register()}}
    }

    private suspend fun register() {
        val user = User(bind.etRegisterEmail.text.toString(), bind.etRegisterPassword.text.toString())
        user.name = bind.etRegisterName.text.toString()
        if (user.email.isNotBlank() && user.password.isNotBlank() && user.name.isNotBlank()) {
            auth.createUserWithEmailAndPassword(user.email, user.password).await()
            auth.currentUser?.let { val save: HashMap<String, String> = HashMap()
                save["Name"] = user.name; save["Email"] = user.email;save["Password"] = md5(user.password)
                database.collection("Users").add(save)
                loadCurrentUser(requireContext())
                startActivity(Intent(context, MainActivity::class.java)).also { activity?.finish() }
            } ?: throw FirebaseAuthException(TAG, "Failed to Register to Firebase.")
        }
    }
}