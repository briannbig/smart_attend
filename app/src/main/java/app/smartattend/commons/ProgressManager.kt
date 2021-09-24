package app.smartattend.commons

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import app.smartattend.commons.alarm.AlarmReceiver
import app.smartattend.db.repository.LessonRepo
import app.smartattend.preferences.AppPreferences

object ProgressManager {
    fun inProgress(context: Context): Boolean {
        return AppPreferences(context).inProgress
    }
    fun startProgress(context: Context, endTime:Long){
        startAlarm(context, endTime)
        AppPreferences(context).inProgress = true
    }
    fun endProgress(context: Context){
        val lessonRepo = LessonRepo(context.applicationContext as Application?)
        lessonRepo.delete()
        AppPreferences(context).inProgress = false
    }
    private fun startAlarm(context: Context, endTime: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
    }
}