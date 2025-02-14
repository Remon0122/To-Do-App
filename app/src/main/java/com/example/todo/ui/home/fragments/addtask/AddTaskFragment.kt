package com.example.todo.ui.home.fragments.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.database.models.MyDatabase
import com.example.todo.database.models.dao.TaskDao
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.util.clearDate
import com.example.todo.ui.util.clearSeconds
import com.example.todo.ui.util.clearTime
import com.example.todo.ui.util.getFormattedTime
import com.example.todo.ui.util.showDatePickerDialog
import com.example.todo.ui.util.showTimePickerDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.R
import com.route.todo.databinding.FragmentAddTaskBinding
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    lateinit var dao : TaskDao
    private var dateCalendar = Calendar.getInstance()
    private var timeCalender = Calendar.getInstance()
    var  onTaskAdded : OnTaskAdded ?= null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = MyDatabase.getInstance().taskDao()
        onSelectDateCalender()
        onSelectTimeCalender()
        OnAddTaskClick()
    }

    private fun onSelectDateCalender() {
        binding.selectDateTv.setOnClickListener{
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
            val calendar = Calendar.getInstance()
            showTimePickerDialog(calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),"Select Time:",childFragmentManager){hour,minute->
                binding.selectTimeTv.text = getFormattedTime(hour,minute)
                timeCalender.set(Calendar.HOUR,hour)
                timeCalender.set(Calendar.MINUTE,minute)
                timeCalender.clearDate()
                timeCalender.clearSeconds()
            }
        }
    }
    fun OnAddTaskClick (){
        binding.addTaskBtn.setOnClickListener {
            if (ValiDateInput())
                return@setOnClickListener

            val task = createTask()
            dao.insertNewTask(task)
            onTaskAdded?.onAddTask(task)

            dismiss()
        }

    }

    private fun createTask():Task{
        return Task(title = binding.title.text.toString(),
            date = dateCalendar.timeInMillis,
            time = timeCalender.timeInMillis,
            description = binding.description.text.toString())
    }

    fun ValiDateInput():Boolean{
        var isValidate = true

        if (binding.title.text.isNullOrBlank()){
            isValidate = false
            binding.titleTil.error = getString(R.string.isrequired)
        }
        if (binding.description.error.isNullOrBlank()){
            isValidate = false
            binding.descriptionTil.error = getString(R.string.isrequired)
        }
        if (binding.selectTimeTv.text.isNullOrBlank()){
            isValidate = false
            binding.selectTimeTil.error = getString(R.string.isrequired)
        }
        return isValidate
    }

    fun interface OnTaskAdded{
        fun onAddTask(task: Task)
    }
}