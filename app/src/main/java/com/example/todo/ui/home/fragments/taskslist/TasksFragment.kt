package com.example.todo.ui.home.fragments.taskslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todo.database.models.entity.Task
import com.example.todo.ui.home.fragments.HomeViewModel.TasksViewModel
import com.example.todo.ui.util.clearTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.route.todo.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.selects.select
import java.util.Calendar

@AndroidEntryPoint
class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val adapter = TaskAdapter()

    private val viewModel : TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTasksBinding.inflate(inflater,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initCalenderView()

        viewModel.tasksList.observe(viewLifecycleOwner){ task->
            adapter.setTasksList(task.toMutableList())
        }
        viewModel.loadTasksByDate(getSelectedDate().timeInMillis)
    }

    fun initRecyclerView(){
        binding.rvTasks.adapter = adapter

        adapter.onDeleteBtnClickListener = TaskAdapter.OnTaskClickListener{ _,task->
            viewModel.deleteTask(task)
        }

        adapter.onDoneBtnClickListener = TaskAdapter.OnTaskClickListener{ _,task ->
            viewModel.markTaskAsDone(task)
        }
    }

    fun initCalenderView(){
        binding.calendarView.selectedDate = CalendarDay.today()

        binding.calendarView.setOnDateChangedListener { _,date,selected->
            if (selected) {
                val calender = Calendar.getInstance().apply {
                    set(Calendar.YEAR, date.year)
                    set(Calendar.MONTH, date.month - 1)
                    set(Calendar.DAY_OF_MONTH, date.day)
                    clearTime()
                }
                viewModel.loadTasksByDate(calender.timeInMillis)
            }
        }
    }

    fun getSelectedDate(): Calendar{
        val calendar = Calendar.getInstance()
        binding.calendarView.selectedDate?.let { date ->
            calendar.set(Calendar.YEAR, date.year)
            calendar.set(Calendar.MONTH, date.month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, date.day)
        }
        calendar.clearTime()
        return calendar
    }

}
