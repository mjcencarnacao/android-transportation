package com.mjceo.transportation.fragments

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.mjceo.transportation.R
import com.mjceo.transportation.databinding.FragmentMainMenuBinding
import com.mjceo.transportation.utils.Utilities.getCurrentUser
import com.mjceo.transportation.utils.Utilities.loadCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMenuFragment : Fragment(R.layout.fragment_main_menu),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMainMenuBinding.bind(view)
        binding.navView.setNavigationItemSelectedListener(this)
        setListeners()
        getLocationPermission()
    }

    private fun setListeners() {
        binding.ivCreditCard.setOnClickListener {
            MainMenuFragmentDirections.mainMenuToWebview("https://ttsl.pt/passageiros/tarifario/")
                .also { findNavController().navigate(it) }
        }
        binding.ivSchedule.setOnClickListener {
            MainMenuFragmentDirections.mainMenuToWebview("https://ttsl.pt/passageiros/horarios-de-ligacoes-fluviais/")
                .also { findNavController().navigate(it) }
        }
        binding.ivHelp.setOnClickListener {
            MainMenuFragmentDirections.mainMenuToWebview("https://ttsl.pt/passageiros/atendimento-ao-cliente/")
                .also { findNavController().navigate(it) }
        }
        binding.ivStops.setOnClickListener { findNavController().navigate(R.id.main_menu_to_stops) }
        binding.ivRealTime.setOnClickListener { findNavController().navigate(R.id.main_menu_to_real_time) }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_leave -> activity?.finishAffinity()
            R.id.nav_logout -> FirebaseAuth.getInstance().signOut()
            R.id.nav_profile -> findNavController().navigate(R.id.main_menu_to_profile)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getLocationPermission() {
        if (checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED)
        else ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_FINE_LOCATION), 1)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(Dispatchers.IO) { loadCurrentUser(requireContext()) }
        Log.e("XXXXXXXXXXXX", getCurrentUser(requireContext()).email)
    }

}