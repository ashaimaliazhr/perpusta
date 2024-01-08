package com.skripsi.perpusta.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.adapter.ItemBorrowAdapter
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.viewmodel.ItemBorrowViewModel


class CirculationFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private val itemBorrowViewModel: ItemBorrowViewModel by viewModels()
    private lateinit var loggedInNpm: String
    private lateinit var itemBorrowAdapter: ItemBorrowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circulation, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        loggedInNpm = sessionManager.getUserId() ?: ""

        val recyclerView: RecyclerView = view.findViewById(R.id.rvDaftarPinjam)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        itemBorrowAdapter = ItemBorrowAdapter(itemBorrowViewModel.circulationList)
        recyclerView.adapter = itemBorrowAdapter

        itemBorrowViewModel.circulationList.observe(viewLifecycleOwner) { circulationList ->
            Log.d("CirculationFragment", "Observer triggered. Length: ${circulationList.size}")
            Log.d("CirculationFragment", "Elements: $circulationList")
            itemBorrowAdapter.submitList(circulationList)
        }

        itemBorrowViewModel.loadData(loggedInNpm)

        val toolbar: Toolbar = view.findViewById(R.id.back_circulation)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_circulationFragment_to_homeFragment)
        }
    }


}