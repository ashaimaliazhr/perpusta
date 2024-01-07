package com.skripsi.perpusta.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.adapter.FineAdapter
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.viewmodel.FineViewModel

class FineFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private val fineViewModel: FineViewModel by viewModels()
    private lateinit var loggedInNpm: String
    private lateinit var fineAdapter: FineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fine, container, false)

        sessionManager = SessionManager(requireContext())
        loggedInNpm = sessionManager.getUserId() ?: ""

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

        val recyclerView: RecyclerView = view.findViewById(R.id.rvListDenda)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        fineAdapter = FineAdapter(fineViewModel.circulationList)
        recyclerView.adapter = fineAdapter

        fineViewModel.circulationList.observe(viewLifecycleOwner) { circulationList ->
            Log.d("FineFragment", "Observer triggered. Length: ${circulationList.size}")
            Log.d("FineFragment", "Elements: $circulationList")
            fineAdapter.submitList(circulationList)
        }

        fineViewModel.loadData(loggedInNpm)


        val toolbar: Toolbar = view.findViewById(R.id.back_fine)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_fineFragment_to_homeFragment)
        }
    }
}