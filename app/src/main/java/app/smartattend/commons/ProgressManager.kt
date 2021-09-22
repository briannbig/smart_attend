package app.smartattend.commons

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import app.smartattend.commons.alarm.AlarmReceiver
import app.smartattend.preferences.AppPreferences

object ProgressManager {
    fun inProgress(context: Context): Boolean {
        return AppPreferences(context).inProgress
    }
    fun checkProgress(context: Context, lessonViewModel: LessonViewModel){
        if(AppPreferences(context).inProgress){
            lessonViewModel.getLesson().value?.apply {
                course = AppPreferences(context).lessonCourseCode
                startTime = AppPreferences(context).lessonStartTime
                endTime = AppPreferences(context).lessonEndTime
            }
        }
    }
    fun startProgress(context: Context, endTime:Long){
        startAlarm(context, endTime)
        AppPreferences(context).inProgress = true
    }
    fun endProgress(context: Context){
        AppPreferences(context).apply {
            inProgress = false
            lessonCourseCode = ""
            lessonStartTime = 0L
            lessonEndTime = 0L
        }
    }
    private fun startAlarm(context: Context, endTime: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
    }
}