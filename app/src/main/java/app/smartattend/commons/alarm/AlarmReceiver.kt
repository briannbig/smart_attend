package app.smartattend.commons.alarm

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import app.smartattend.commons.ProgressManager
import app.smartattend.db.repository.LessonRepo

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
//        end progress
        val lessonRepo = LessonRepo(context.applicationContext as Application?)
        lessonRepo.delete()
        ProgressManager.endProgress(context)
//        show notification
        val notificationUtils = NotificationUtils(context)
        val notification = notificationUtils.getNotificationBuilder().build()
        notificationUtils.getManager().notify(150, notification)
    }
}