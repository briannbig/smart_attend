package app.smartattend.commons.alarm

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressListener
import app.smartattend.commons.ProgressManager
import app.smartattend.db.repository.LessonRepo

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        ProgressListener().onProgressEnd(context)
//        val ctx = context.applicationContext as Application?
    }

}