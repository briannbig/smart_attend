package app.smartattend.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseDB {
    private var database = Firebase.database.apply { setPersistenceEnabled(true) }
    val lecturerRef = database.getReference("Lecturers")
    val lessonRef = database.getReference("Lessons")
    val courseRef = database.getReference("Courses")
    private val studentRef = database.getReference("Students")
    val userRef = database.getReference("Users")
    val classRef = database.getReference("Classes")
}