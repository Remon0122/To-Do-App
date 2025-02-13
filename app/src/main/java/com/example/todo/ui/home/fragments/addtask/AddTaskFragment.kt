package com.example.todo.ui.home.fragments.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.ui.util.clearDate
import com.example.todo.ui.util.clearSeconds
import com.example.todo.ui.util.clearTime
import com.example.todo.ui.util.getFormattedTime
import com.example.todo.ui.util.showDatePickerDialog
import com.example.todo.ui.util.showTimePickerDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.todo.databinding.FragmentAddTaskBinding
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddTaskBinding
    private var dateCalendar = Calendar.getInstance()
    private var timeCalender = Calendar.getInstance()

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
        onSelectDateCalender()
        onSelectTimeCalender()
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

}