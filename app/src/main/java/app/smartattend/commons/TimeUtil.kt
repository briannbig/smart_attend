package app.smartattend.commons

import java.text.DateFormat
import java.util.*

object CalenderUtil {
    private val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
    private val tf = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.getDefault())
    private val currentTime : Long = Calendar.getInstance().timeInMillis
    val isToday: (Long) -> Boolean = fun (timeStamp: Long): Boolean {
        return (df.format(currentTime) == df.format(timeStamp))
    }
    fun longToTime(longMillis: Long): String{
        return tf.format(longMillis)
    }
}