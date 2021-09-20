package app.smartattend.lecturer.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import app.smartattend.R
import app.smartattend.commons.LessonViewModel
import java.util.*

class TimePickerFragment(private val textView: TextView, private val lessonViewModel: LessonViewModel) : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var timeVar1: Long? = null
    private lateinit var mCalendar: Calendar

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mCalendar = Calendar.getInstance()
        val calender = Calendar.getInstance()
        val hour = calender.get(Calendar.HOUR_OF_DAY)
        val minute = calender.get(Calendar.MINUTE)
        return TimePickerDialog(activity, this, hour, minute, is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        updateCal(hourOfDay, minute)
    }
    private fun updateCal(hourOfDay: Int, minute: Int){
        mCalendar.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        timeVar1 = mCalendar.timeInMillis
        when(textView.id){

            R.id.tvEndTime -> {
                lessonViewModel.endTime.value = timeVar1 as Long
            }
        }
        textView.text = "$hourOfDay : $minute"

    }
}