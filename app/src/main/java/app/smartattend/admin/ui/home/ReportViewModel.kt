package app.smartattend.admin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import app.smartattend.reports.ReportGen

class ReportViewModel : ViewModel() {
    var reportItems = MutableLiveData<ArrayList<ReportItem>>()
    var course = MutableLiveData<Course>()

    suspend fun fetch(): ArrayList<ReportItem>? {
        reportItems.value = ReportGen().analyzeForSpecificCourse(course.value!!)
        return reportItems.value
    }

}