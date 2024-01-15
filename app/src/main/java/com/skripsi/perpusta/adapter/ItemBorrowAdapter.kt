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
import com.skripsi.perpusta.model.circulation.status.Data

class ItemBorrowAdapter(
    private val circulationStatus: LiveData<List<Data?>>
    ): ListAdapter<Data, ItemBorrowAdapter.ViewHolder>(DiffCallback()) {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val tvTitleStatus: TextView = itemView.findViewById(R.id.tvTitleStatus)
            val tvTanggalPinjam: TextView =  itemView.findViewById(R.id.tvTanggalPinjam)
            val tvTanggalKembali: TextView = itemView.findViewById(R.id.tvTanggalKembali)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_borrow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val circulationList = currentList
        if (circulationList != null && circulationList.isNotEmpty()) {
            val circulationItem = circulationList[position]

            holder.tvTitleStatus.text = circulationItem?.cItem?.eTitBib?.eTit?.titKey
            holder.tvTanggalPinjam.text = ("Tanggal Pinjam: " + circulationItem?.chkODate)
            holder.tvTanggalKembali.text = ("Tenggat Waktu: " + circulationItem?.dueDate)

            Log.d("ItemBorrowAdapter", "Title: ${circulationItem?.cItem?.eTitBib?.eTit?.titKey}")
            Log.d("ItemBorrowAdapter", "ChkODate: ${circulationItem?.chkODate}")
            Log.d("ItemBorrowAdapter", "DueDate: ${circulationItem?.dueDate}")
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem?.iD == newItem.iD
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return circulationStatus.value?.size ?: 0
    }


}