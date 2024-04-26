package com.skripsi.perpusta.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.data.room.TaskEntity
import com.skripsi.perpusta.databinding.ItemTaskBinding
import com.skripsi.perpusta.viewmodel.TaskViewModel

class TaskListAdapter( private val viewModel: TaskViewModel) : ListAdapter<TaskEntity, TaskListAdapter.TaskViewHolder>(DiffCallback()) {
    var listenerDelete: (TaskEntity) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun deleteTask(position: Int) {
        val deleteTask = getItem(position)
        viewModel.deleteTask(deleteTask)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onDeleteButtonClick(){
            deleteTask(adapterPosition)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: TaskEntity) {
            binding.tvTitleTask.text = item.title
            binding.tvDate.text = "${item.date} ${item.hour}"


            binding.ivMore.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: TaskEntity) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem == newItem
    }
}