package com.skripsi.perpusta.ui

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.skripsi.perpusta.R
import com.skripsi.perpusta.data.room.AddTaskListener
import com.skripsi.perpusta.adapter.TaskListAdapter
import com.skripsi.perpusta.data.room.AppDatabase
import com.skripsi.perpusta.data.room.TaskEntity
import com.skripsi.perpusta.extensions.ReminderBroadcastReceiver
import com.skripsi.perpusta.model.task.Task
import com.skripsi.perpusta.viewmodel.TaskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class AddTaskFragment : Fragment(), AddTaskListener {

    private lateinit var tilTitle: TextInputLayout
    private lateinit var tilDate: TextInputLayout
    private lateinit var tilTimer: TextInputLayout

    private lateinit var selectedCalendar: Calendar
    private var selectedReminderTime: Long = 0

    private lateinit var listener: AddTaskListener
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)

        tilTitle = view.findViewById(R.id.til_title)
        tilDate = view.findViewById(R.id.til_date)
        tilTimer = view.findViewById(R.id.til_timer)

        val buttonCancel: MaterialButton = view.findViewById(R.id.button_cancel)
        val buttonNewTask: MaterialButton = view.findViewById(R.id.button_new_task)

        selectedCalendar = Calendar.getInstance()

        tilDate.editText?.setOnClickListener { showDatePicker() }
        tilTimer.editText?.setOnClickListener { showTimePicker() }

        buttonCancel.setOnClickListener { activity?.onBackPressed() }
        buttonNewTask.setOnClickListener {
            Log.d("AddTaskFragment", "Button Clicked")
            saveTask()
        }

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        setAddTaskListener(object : AddTaskListener {
            override fun onTaskAdded(taskEntity: TaskEntity) {
                this@AddTaskFragment.onTaskAdded(taskEntity)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskListAdapter = TaskListAdapter(viewModel)

    }

    override fun onTaskAdded(taskEntity: TaskEntity) {
        val updateList = taskListAdapter.currentList.toMutableList()
        updateList.add(taskEntity)
        taskListAdapter.submitList(updateList)
    }

    private fun showTimePicker() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)

                updateTimerUI()
            },
            selectedCalendar.get(Calendar.HOUR_OF_DAY),
            selectedCalendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }


    private fun updateTimerUI() {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        tilTimer.editText?.setText(timeFormat.format(selectedCalendar.time))
    }

    private fun showDatePicker() {
       val datePickerDialog = DatePickerDialog(
           requireContext(),
           DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
               selectedCalendar.set(Calendar.YEAR, year)
               selectedCalendar.set(Calendar.MONTH, month)
               selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

               updateDateUi()
           },
           selectedCalendar.get(Calendar.YEAR),
           selectedCalendar.get(Calendar.MONTH),
           selectedCalendar.get(Calendar.DAY_OF_MONTH)
       )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun updateDateUi() {
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        tilDate.editText?.setText(dateFormat.format(selectedCalendar.time))
    }

    private fun saveTask() {
        Log.d("AddTaskFragment", "saveTask() called")

        val task = Task(
            title = tilTitle.editText?.text.toString(),
            hour = tilTimer.editText?.text.toString(),
            date = tilDate.editText?.text.toString(),
            reminderTime = selectedReminderTime
        )

        val taskEntity = TaskEntity(
            title = task.title,
            hour = task.hour,
            date = task.date,
            reminderTime = task.reminderTime,
        )

        val database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "task-database"
        ).build()

        GlobalScope.launch(Dispatchers.IO) {
            database.taskDao().insert(taskEntity)
            Log.d("AddTaskFragment", "Task inserted into database")
        }
        viewModel.insertTask(taskEntity)
        Log.d("AddTaskFragment", "Task inserted into ViewModel")
        setReminder(task)
        Log.d("AddTaskFragment", "Reminder set successfully")
        if (::listener.isInitialized) {
            listener.onTaskAdded(taskEntity)
            Log.d("AddTaskFragment", "Listener callback invoked")
            navigateToReminderFragment()
        } else {
            Log.e("AddTaskFragment", "Listener is not initialized")
        }
    }

    private fun navigateToReminderFragment() {
        val navController = findNavController()
        navController.navigate(R.id.action_addTaskFragment_to_reminderFragment)
    }

    private fun setReminder(task: Task) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(requireContext(), ReminderBroadcastReceiver::class.java)
        intent.putExtra("title", task.title)
        intent.putExtra("date", task.date)
        intent.putExtra("hour", task.hour)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val reminderTime = task.reminderTime

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            reminderTime,
            pendingIntent
        )

    }

    fun setAddTaskListener(listener: AddTaskListener) {
        this.listener = listener
    }
}