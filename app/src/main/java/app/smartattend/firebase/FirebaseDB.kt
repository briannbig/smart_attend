package app.smartattend.firebase

import app.smartattend.model.User
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object FirebaseDB {
    private var database = Firebase.database.apply { setPersistenceEnabled(true) }
    private val lecturerRef = database.getReference("Lecturers")
    private val lessonRef = database.getReference("Lessons")
    private val courseRef = database.getReference("Courses")
    private val studentRef = database.getReference("Students")
    private val userRef = database.getReference("Users")
    private val classRef = database.getReference("Classes")

    fun insertUser(user: User): Boolean {
        return true
    }
    fun getUser(): User{
        return User()
    }
}