package app.smartattend.commons.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import app.smartattend.commons.ProgressManager

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        end progress
        ProgressManager.endProgress(context)
//        show notification
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(150, notification)
    }
}