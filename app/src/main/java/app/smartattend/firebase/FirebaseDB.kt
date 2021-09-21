package app.smartattend.firebase

import androidx.lifecycle.LiveData
import app.smartattend.model.Lecturer
import app.smartattend.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

    private var lecturers = arrayListOf<Lecturer>()
    fun insertUser(user: User): Boolean {
        return true
    }
    fun getUser(): User{
        return User()
    }
    fun insertLecturer(lecturer: Lecturer){

    }
    fun getLecturers(): ArrayList<Lecturer>{
        lecturerRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return lecturers
    }
}