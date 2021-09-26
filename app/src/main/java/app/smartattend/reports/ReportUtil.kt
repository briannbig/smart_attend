package app.smartattend.reports

import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import com.google.firebase.database.DataSnapshot

class ReportUtil {

    private var courseReport = ArrayList<ReportItem>()
    fun analyzeForSpecificCourse(course: Course): ArrayList<ReportItem> {
        val lessonSnapshot = FirebaseDB.lessonRef.orderByChild("course").equalTo(course.code).get()
        val studentsSnapshot = FirebaseDB.studentRef.orderByChild("classId").equalTo(course.classId).get()
        lessonSnapshot.addOnSuccessListener {
            val lesSnapshot = it
            studentsSnapshot.addOnSuccessListener {
                for (i in it.children){
                    analyzeStudPercentage(lesSnapshot, i.child("reg_no").value.toString())
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