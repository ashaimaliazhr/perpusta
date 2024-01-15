package com.skripsi.perpusta.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.model.circulation.history.Data

class ItemHistoryAdapter(
    private val circulationHistory: LiveData<List<Data?>>
) : ListAdapter<Data, ItemHistoryAdapter.ViewHolder>(DiffCallback()){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitleHistory: TextView = itemView.findViewById(R.id.tvTitleHistory)
        val tvChkOTime: TextView = itemView.findViewById(R.id.tvChkODate)
        val tvDueDateHistory: TextView = itemView.findViewById(R.id.tvDueDateHistory)
        val tvChkITime: TextView = itemView.findViewById(R.id.tvChkIDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val circulationList = currentList
        if (circulationList != null && circulationList.isNotEmpty()) {
            val circulationItem = circulationList[position]

            Log.d("ItemHistoryAdapter", "Position: $position, CirculationItem: $circulationItem")

            Log.d("ItemHistoryAdapter", "Title: ${circulationItem?.cItem?.eTitBib?.eTit?.titKey}")
            Log.d("ItemHistoryAdapter", "Due Date: ${circulationItem?.chkODate}")
            Log.d("ItemHistoryAdapter", "Fine Amount: ${circulationItem?.dueDate}")
            Log.d("ItemHistoryAdapter", "Fine Amount: ${circulationItem?.chkIDate}")
            holder.tvTitleHistory.text = circulationItem?.cItem?.eTitBib?.eTit?.titKey
            holder.tvChkOTime.text = ("Tanggal Pinjam: " + circulationItem?.chkODate)
            holder.tvDueDateHistory.text = ("Tenggat Waktu: " + circulationItem?.dueDate)
            holder.tvChkITime.text = ("Tanggal Kembali: " + circulationItem?.chkIDate)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem?.iD == newItem.iD
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return circulationHistory.value?.size ?: 0
    }



}