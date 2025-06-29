package com.example.todo.ui.home.fragments.addtask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.util.clearSeconds
import com.example.todo.ui.util.clearTime
import com.example.todo.ui.util.getFormattedTime
import com.example.todo.ui.util.showDatePickerDialog
import com.example.todo.ui.util.showTimePickerDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.R
import com.route.todo.databinding.FragmentAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    @Inject
    lateinit var dao : TaskDao
    private var dateCalendar = Calendar.getInstance()
    private var timeCalender = Calendar.getInstance()
    private val calendar = Calendar.getInstance()
    var  onTaskAdded : OnTaskAdded ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSelectDateCalender()
        onSelectTimeCalender()
        OnAddTaskClick()
    }

    private fun onSelectDateCalender() {
        binding.selectDateTv.setOnClickListener{
            //compare
            showDatePickerDialog(requireContext()) { date, calender ->
                binding.selectDateTv.text = date
                dateCalendar.set(Calendar.YEAR, calender.get(Calendar.YEAR))
                dateCalendar.set(Calendar.MONTH, calender.get(Calendar.MONTH))
                dateCalendar.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH))
                dateCalendar.clearTime()
            }
        }
    }
    private fun onSelectTimeCalender() {
        binding.selectTimeTv.setOnClickListener {
            showTimePickerDialog(calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),
                "Select Time:", childFragmentManager) { hour, minute ->
                binding.selectTimeTv.text = getFormattedTime(hour, minute)
                timeCalender.set(Calendar.HOUR, hour)
                timeCalender.set(Calendar.MINUTE, minute)
                timeCalender.clearSeconds()
            }
        }
    }
    fun OnAddTaskClick () {
        binding.addTaskBtn.setOnClickListener {
            if (!validateInput())
                return@setOnClickListener

            val task = createTask()
            lifecycleScope.launch {
                dao.insertNewTask(task)
                Log.e("TAG", "$task")
                onTaskAdded?.onAddTask(task)

                val allTasks = dao.getAllTasks()
                Log.e("TAG", "$allTasks")

                dismiss()
            }
        }
    }

    private fun createTask():Task{
        return Task(title = binding.title.text.toString(),
            date = dateCalendar.timeInMillis,
            time = timeCalender.timeInMillis,
            description = binding.description.text.toString())
    }

    fun validateInput():Boolean{
        var isValid = true
        if (binding.title.text.isNullOrBlank()){
            isValid = false
            binding.titleTil.error = getString(R.string.isrequired)
        }
        if (binding.selectDateTv.text.isNullOrBlank()){
            isValid = false
            binding.selectDateTil.error = getString(R.string.isrequired)
        }
        if (binding.selectTimeTv.text.isNullOrBlank()){
            isValid = false
            binding.selectTimeTil.error = getString(R.string.isrequired)
        }
        return isValid
    }

    fun interface OnTaskAdded{
        fun onAddTask(task: Task)
    }
}