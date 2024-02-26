package com.skripsi.perpusta.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.perpusta.R
import com.skripsi.perpusta.adapter.TaskListAdapter
import com.skripsi.perpusta.data.datastore.SessionManager
import com.skripsi.perpusta.data.room.AddTaskListener
import com.skripsi.perpusta.data.room.TaskEntity
import com.skripsi.perpusta.databinding.FragmentReminderBinding
import com.skripsi.perpusta.viewmodel.TaskViewModel

class ReminderFragment : Fragment(), AddTaskListener {

    private lateinit var binding: FragmentReminderBinding
    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var sessionManager: SessionManager

    private lateinit var listener: AddTaskListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentReminderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        taskListAdapter = TaskListAdapter(taskViewModel)

        setAddTaskListener(this)
        setupRecyclerView()
        observeTaskList()
        setupFabClickListener()

        val toolbar: Toolbar = view.findViewById(R.id.back_reminder)

        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_reminderFragment_to_homeFragment)
        }
    }

    private fun setAddTaskListener(listener: AddTaskListener) {
        this.listener = listener
    }

    private fun setupFabClickListener() {
        binding.fab.setOnClickListener {
           showAddTaskFragment()
        }
    }

//    private fun observeTaskList() {
//       taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
//           tasks?.let {
//               Log.d("ReminderFragment", "Number of tasks: ${it.size}")
//               if(it.isEmpty()) {
//                   showEmptyTaskView()
//               } else {
//                   showRecyclerView(it)
//               }
//           }
//       })
//    }

    private fun observeTaskList() {
        val userId = sessionManager.getUserId() // Ambil ID pengguna yang sedang login
        if (userId == null) {
            Log.e("ReminderFragment", "ID Pengguna kosong")
            return // Hentikan pengamatan tugas jika ID pengguna kosong
        }

        taskViewModel.getTasksByUserId(userId).observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {
                Log.d("ReminderFragment", "Jumlah tugas: ${it.size}")
                if(it.isEmpty()) {
                    showEmptyTaskView()
                } else {
                    showRecyclerView(it)
                }
            }
        })
    }

    private fun showEmptyTaskView() {
       binding.rvTask.visibility = View.GONE
       binding.emptyInclude.stateEmptyCs.visibility = View.VISIBLE
    }

    private fun showRecyclerView(tasks: List<TaskEntity>) {
        binding.rvTask.visibility = View.VISIBLE
        binding.emptyInclude.stateEmptyCs.visibility = View.GONE
        taskListAdapter.submitList(tasks)
    }


//    private fun showEditTaskDialog(task: Task) {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Edit Task")
//
//        val editText = EditText(requireContext())
//        editText.setText(task.title)
//
//        builder.setView(editText)
//
//        builder.setPositiveButton("Save") {_, _ ->
//            val editedTitle = editText.text.toString()
//        }
//
//    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView? = view?.findViewById(R.id.rvTask)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = taskListAdapter

        taskListAdapter.listenerDelete = { taskEntity: TaskEntity ->
            showDeleteTaskDialog(taskEntity)
        }

        taskViewModel.allTasks.observe(viewLifecycleOwner, Observer { tasks ->
            tasks?.let {
                Log.d("ReminderFragment", "Number of tasks: ${tasks.size}")
                taskListAdapter.submitList(it)
            }
        })
    }

    private fun showDeleteTaskDialog(taskEntity: TaskEntity) {
        taskViewModel.deleteTask(taskEntity)
    }

    private fun showAddTaskFragment() {
        val navController = findNavController()
        navController.navigate(R.id.action_reminderFragment_to_addTaskFragment)
    }

    override fun onTaskAdded(taskEntity: TaskEntity) {
        val updateList = taskListAdapter.currentList.toMutableList()
        updateList.add(taskEntity)
        taskListAdapter.submitList(updateList)
    }

}