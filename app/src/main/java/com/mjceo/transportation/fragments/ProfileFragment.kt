package com.mjceo.transportation.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.mjceo.transportation.R
import com.mjceo.transportation.databinding.FragmentProfileBinding
import com.mjceo.transportation.utils.ImageUtil.loadImageWithCustomSize
import com.mjceo.transportation.utils.SharedPreferencesManager.customPrefs
import com.mjceo.transportation.utils.Utilities
import com.mjceo.transportation.utils.Utilities.getCurrentUser
import com.mjceo.transportation.utils.models.User

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val image = registerForActivityResult(ActivityResultContracts.GetContent()) {
        loadImageWithCustomSize(requireContext(), binding.ivProfilePicture, it.toString(), 200, 200)
        val user = getCurrentUser(requireContext())
        user.image = it.toString()
        customPrefs(requireContext()).edit().putString("User", Gson().toJson(user)).apply()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentProfileBinding.bind(view)
        buildView()
        binding.ivProfilePicture.setOnClickListener { image.launch("image/*") }
    }

    private fun buildView() {
        val user = getCurrentUser(requireContext())
        binding.tvProfileName.text = user.name
        binding.tvProfileEmail.text = user.email
        binding.tvVivaCard.text = user.card
        if (user.image != null)
            loadImageWithCustomSize(requireContext(), binding.ivProfilePicture, user.image, 200, 200)
    }

    override fun onResume() {
        super.onResume()
        buildView()
    }
}