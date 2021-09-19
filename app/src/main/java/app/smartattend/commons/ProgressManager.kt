package app.smartattend.commons

import android.content.Context
import app.smartattend.preferences.AppPreferences

object ProgressManager {
    fun inProgress(context: Context): Boolean {
        return AppPreferences(context).inProgress
    }
    fun startProgress(context: Context){
        AppPreferences(context).inProgress = true
    }
    fun endProgress(context: Context){
        AppPreferences(context).inProgress = false
    }
}