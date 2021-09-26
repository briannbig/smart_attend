package app.smartattend.commons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.smartattend.model.Course

class CourseViewModel: ViewModel() {
    var course = MutableLiveData<Course>()
    var courseCode = MutableLiveData<String>()
    fun updateClass(course: Course) {
        this.course.value = course
    }
}