package app.smartattend.reports

import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReportGen {

    private var allReg = ArrayList<String>()
    private var courseReport = ArrayList<ReportItem>()
    fun analyzeForSpecificCourse(course: Course): ArrayList<ReportItem> {
        val lessonSnapshot = FirebaseDB.lessonRef.orderByChild("course").equalTo(course.code).get()
        val studentsSnapshot = FirebaseDB.studentRef.orderByChild("enrolledClass").equalTo(course.classId).get()
        lessonSnapshot.addOnSuccessListener {
            if (it.exists()){
                for (i in it.children) {
                    allReg.add(it.child("attendees/reg_No").value.toString())
                }
            }
        }
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