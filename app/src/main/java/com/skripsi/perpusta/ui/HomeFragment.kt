package com.skripsi.perpusta.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.viewmodel.CirculationViewModel

class HomeFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    val circulationViewModel: CirculationViewModel by viewModels()
    private lateinit var loggedInNpm: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireContext())

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

        sessionManager = SessionManager(requireContext())

        val fullName = sessionManager.getFullName()
        val tvNamaUser = view.findViewById<TextView>(R.id.tv_namaUser)
        tvNamaUser.text = fullName

        val logoutImageView = view.findViewById<ImageView>(R.id.logout)
        logoutImageView.setOnClickListener{
            showLogoutConfirmationDialog()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        loggedInNpm = sessionManager.getUserId() ?: ""

        circulationViewModel.circulationHistoryLength.observe(viewLifecycleOwner, { length ->
            Log.d("Home Fragment", "Observer triggered. Length: $length")
            val tvPeminjaman = view.findViewById<TextView>(R.id.tvPeminjaman)
            tvPeminjaman.text = "Jumlah Buku: $length"
        })

        circulationViewModel.getCirculationHistory(loggedInNpm)

        circulationViewModel.circulationStatusLength.observe(viewLifecycleOwner, { length ->
            val tvStatusKembali = view.findViewById<TextView>(R.id.tvStatusKembali)
            tvStatusKembali.text = "Jumlah buku: $length"
        })

        circulationViewModel.getCirculationStatus(loggedInNpm)

        circulationViewModel.circulationAccountLength.observe(viewLifecycleOwner, { length ->
            val tvStatusDenda = view.findViewById<TextView>(R.id.tvStatusDenda)
            tvStatusDenda.text = "Jumlah Buku didenda: $length"
        })

        circulationViewModel.getCirculationAccount(loggedInNpm)


    }

    private fun showLogoutConfirmationDialog(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Confirmation Logout")
            .setMessage("Are You Sure to Logout?")
            .setPositiveButton("Yes") { _, _ ->
                navigateToLogin()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment)
    }

}