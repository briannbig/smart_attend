package app.smartattend.commons

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

//  this utility class facilitates changing time stored in our database in Long primitive type
// to more human understandable format.
// formats the given Long to dd/MM/yyyy HH:mm
object TimeConverter {
    fun toDateFormat(timeInMillis: Long): String {
        val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)
        val date = Date(timeInMillis)
        return dateFormat.format(date)
    }
}