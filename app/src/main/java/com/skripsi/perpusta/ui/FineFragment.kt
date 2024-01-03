package com.skripsi.perpusta.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.skripsi.perpusta.R
import com.skripsi.perpusta.data.datastore.SessionManager

class FineFragment : Fragment() {
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fine, container, false)

        sessionManager = SessionManager(requireContext())

        val fullName = sessionManager.getFullName()
        val tvNamaUser = view.findViewById<TextView>(R.id.tvNama)
        tvNamaUser.text = fullName

        val id = sessionManager.getUserId()
        val tvId = view.findViewById<TextView>(R.id.tvNpm)
        tvId.text = id

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.back_fine)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fineFragment_to_homeFragment)
        }
    }
}