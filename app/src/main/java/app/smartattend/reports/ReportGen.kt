package app.smartattend.reports

import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import app.smartattend.reports.models.Report
import com.google.firebase.database.Query

class ReportGen {

    private lateinit var studSnapshot: Query
    private var report = Report;
    suspend fun analyzeForSpecificCourse(course: Course): ArrayList<ReportItem>? {
        val lessonSnapshotQuery = FirebaseDB.lessonRef.orderByChild("course").equalTo(course.code)
        studSnapshot = FirebaseDB.studentRef.orderByChild("enrolledClass").equalTo(course.classId)
        lessonSnapshotQuery.get().addOnSuccessListener {
            if (it.exists())report.post(it)
        }
        return if (report.isSnapNull()) {
            report.generateReport()
        } else null
    }

}