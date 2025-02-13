package com.example.todo.ui.util

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

fun showDatePickerDialog(context: Context,callback:(String,Calendar) -> Unit){
    val dialog = DatePickerDialog(context)
    dialog.datePicker.minDate = System.currentTimeMillis()
    dialog.setOnDateSetListener{picker ,year,month,day->

        val calender = Calendar.getInstance()
        calender.set(Calendar.YEAR,year)
        calender.set(Calendar.MONTH,month)
        calender.set(Calendar.DAY_OF_YEAR,day)
        calender.clearTime()
        callback( "$day/${month+1}/$year",calender)


    }
    dialog.show()
}
fun showTimePickerDialog(hour:Int, minute:Int, title:String, fragmentManager: FragmentManager, callback:(Int, Int)->Unit) {
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(hour)
        .setMinute(minute)
        .setTitleText(title)
        .build()

    picker.show(fragmentManager, "")
    picker.addOnPositiveButtonClickListener {
        callback(picker.hour, picker.minute)
    }

    picker.addOnNegativeButtonClickListener {
        picker.dismiss()
    }
}