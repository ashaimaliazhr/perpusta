package com.skripsi.perpusta.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.adapter.ItemBorrowAdapter
import com.skripsi.perpusta.adapter.ItemHistoryAdapter
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.viewmodel.ItemBorrowViewModel
import com.skripsi.perpusta.viewmodel.ItemHistoryViewModel


class CirculationFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private val itemBorrowViewModel: ItemBorrowViewModel by viewModels()
    private val itemHistoryViewModel: ItemHistoryViewModel by viewModels()
    private lateinit var loggedInNpm: String
    private lateinit var itemBorrowAdapter: ItemBorrowAdapter
    private lateinit var itemHistoryAdapter: ItemHistoryAdapter

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

        val firstRecyclerView: RecyclerView = view.findViewById(R.id.rvDaftarPinjam)
        firstRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val secondRecyclerView: RecyclerView = view.findViewById(R.id.rvRiwayatPinjam)
        secondRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val emptyImageBorrow : ImageView = view.findViewById(R.id.empty_image_borrow)
        val emptyMessageBorrow : TextView = view.findViewById(R.id.empty_message_borrow)
        val emptyImageHistory : ImageView = view.findViewById(R.id.empty_image_history)
        val emptyMessageHistory : TextView = view.findViewById(R.id.empty_message_history)


        itemBorrowAdapter = ItemBorrowAdapter(itemBorrowViewModel.circulationList)
        firstRecyclerView.adapter = itemBorrowAdapter

        itemHistoryAdapter = ItemHistoryAdapter(itemHistoryViewModel.circulationHistory)
        secondRecyclerView.adapter = itemHistoryAdapter

        itemBorrowViewModel.circulationList.observe(viewLifecycleOwner) { circulationList ->
            Log.d("CirculationFragment", "Observer triggered. Length: ${circulationList.size}")
            Log.d("CirculationFragment", "Elements: $circulationList")


            if (circulationList.isEmpty()) {
                emptyImageBorrow.visibility = View.VISIBLE
                emptyMessageBorrow.visibility = View.VISIBLE
                firstRecyclerView.visibility = View.GONE
            } else {
                emptyImageBorrow.visibility = View.GONE
                emptyMessageBorrow.visibility = View.GONE
                firstRecyclerView.visibility = View.VISIBLE
                itemBorrowAdapter.submitList(circulationList)
            }
        }
        itemBorrowViewModel.loadData(loggedInNpm)

        itemHistoryViewModel.circulationHistory.observe(viewLifecycleOwner) { circulationHistory ->
            Log.d("CirculationFragment", "Observer triggered. Length: ${circulationHistory.size}")
            Log.d("CirculationFragment", "Elements: $circulationHistory")

            if (circulationHistory.isEmpty()) {
                emptyImageHistory.visibility = View.VISIBLE
                emptyMessageHistory.visibility = View.VISIBLE
                secondRecyclerView.visibility = View.GONE
            } else {
                emptyImageHistory.visibility = View.GONE
                emptyMessageHistory.visibility = View.GONE
                secondRecyclerView.visibility = View.VISIBLE
                itemHistoryAdapter.submitList(circulationHistory)
            }
        }

        itemHistoryViewModel.loadData(loggedInNpm)

        val toolbar: Toolbar = view.findViewById(R.id.back_circulation)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_circulationFragment_to_homeFragment)
        }
    }


}