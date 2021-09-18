package app.smartattend.preferences

import android.content.Context
import android.content.SharedPreferences
import app.smartattend.Constants
import app.smartattend.Constants.IS_ADMIN
import app.smartattend.Constants.IS_LECTURER
import app.smartattend.Constants.LOGGED_IN

class AppPreferences(private val ctx: Context) {

    val preferences: SharedPreferences = ctx.getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
    var logged_in = preferences.getBoolean(LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(LOGGED_IN, value).apply()
    var isAdmin = preferences.getBoolean(IS_ADMIN, false)
        set(value) = preferences.edit().putBoolean(IS_ADMIN, value).apply()
    var isLecturer = preferences.getBoolean(IS_LECTURER, false)
        set(value) = preferences.edit().putBoolean(IS_LECTURER, value).apply()
}