package com.mjceo.transportation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mjceo.transportation.R
import com.mjceo.transportation.activities.MainActivity
import kotlinx.coroutines.*

class StartupFragment : Fragment(R.layout.fragment_startup) {
    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val logo = view.findViewById<ImageView>(R.id.ivMainLogo)
        logo.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.rotate_animation))
        lifecycleScope.launch(Dispatchers.IO) { delay(3000).also {
            if (auth.currentUser != null)
                startActivity(Intent(context, MainActivity::class.java)).also { activity?.finish() }
            else withContext(Dispatchers.Main){findNavController().navigate(R.id.startup_to_login) }}}
    }
}