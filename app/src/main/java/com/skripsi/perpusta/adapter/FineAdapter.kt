package com.skripsi.perpusta.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.model.circulation.account.AccountResponse
import com.skripsi.perpusta.model.circulation.account.Data


class FineAdapter(
    private val circulationAccount: LiveData<List<Data?>>
) : ListAdapter<Data, FineAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitleBook: TextView = itemView.findViewById(R.id.tvTitleBook)
        val tvDueDate: TextView = itemView.findViewById(R.id.tvDueDate)
        val tvFine: TextView = itemView.findViewById(R.id.tvFine)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fine, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circulationList = currentList
        if (circulationList != null && circulationList.isNotEmpty()) {
            val circulationItem = circulationList[position]


            Log.d("FineAdapter", "Position: $position, CirculationItem: $circulationItem")

            Log.d("FineAdapter", "Title: ${circulationItem?.cItem?.eTitBib?.eTit?.titKey}")
            Log.d("FineAdapter", "Due Date: ${circulationItem?.chkODate}")
            Log.d("FineAdapter", "Fine Amount: ${circulationItem?.fineAmnt}")
            holder.tvTitleBook.text = circulationItem?.cItem?.eTitBib?.eTit?.titKey
            holder.tvDueDate.text = circulationItem?.chkODate
            holder.tvFine.text = circulationItem?.fineAmnt.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem?.iD == newItem.iD
        }

        override fun areContentsTheSame(oldItem:Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount(): Int {
        return circulationAccount.value?.size ?: 0
    }


}