package app.smartattend.reports

import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReportUtil {

    private var courseReport = ArrayList<ReportItem>()
    fun analyzeForSpecificCourse(course: Course): ArrayList<ReportItem> {
        val lessonSnapshot = FirebaseDB.lessonRef.orderByChild("course").equalTo(course.code)
        val studentsSnapshot = FirebaseDB.studentRef.orderByChild("enrolledClass").equalTo(course.classId)
        lessonSnapshot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val lesSnapshot = snapshot
                    studentsSnapshot.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children){
                                analyzeStudPercentage(lesSnapshot, i.child("reg_no").value.toString())
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return courseReport
    }
    private fun analyzeStudPercentage(snapshot: DataSnapshot, reg_no: String){
        var sum = 0; var count = 0
        for (i in snapshot.children){
            if (i.hasChild(reg_no)){
                sum ++
            }
            count ++
        }
        val avg = (sum/count) * 100
        courseReport.add(ReportItem(reg_no, avg))
    }
}