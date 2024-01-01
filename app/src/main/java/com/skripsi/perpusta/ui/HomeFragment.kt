package com.skripsi.perpusta.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skripsi.perpusta.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //set up bottom nav
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val navController = Navigation.findNavController(requireActivity(), R.id.fragmentContaineView)

            when (item.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_circulation -> {
                    navController.navigate(R.id.circulationFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    navController.navigate(R.id.reminderFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_account -> {
                    navController.navigate(R.id.fineFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        return view
    }

}