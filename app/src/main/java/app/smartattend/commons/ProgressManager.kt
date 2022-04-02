package app.smartattend.commons

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import app.smartattend.commons.alarm.AlarmReceiver
import app.smartattend.commons.alarm.NotificationUtils
import app.smartattend.db.AppDb
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
        val ctx = context.applicationContext as Application
        AppDb.databaseWriteExecutor.execute { AppDb.getInstance(ctx).appDao().deleteLesson() }
        AppPreferences(context).inProgress = false
    }
    private fun startAlarm(context: Context, endTime: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, endTime, pendingIntent)
    }
}

interface ProgressListenerInterface{
    fun onProgressStart(context: Context, endTime: Long)
    fun onProgressEnd(context: Context)
}

class ProgressListener : ProgressListenerInterface{
    override fun onProgressStart(context: Context, endTime: Long) {
        ProgressManager.startProgress(context, endTime)
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getStartProgressNotificationBuilder().build()
        notificationUtils.getManager().notify(151, notification)
    }

    override fun onProgressEnd(context: Context) {
        ProgressManager.endProgress(context.applicationContext)
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(150, notification)
    }

}