package app.smartattend.student.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import app.smartattend.model.Student
import app.smartattend.preferences.AppPreferences

class StudentViewModel(application: Application) : AndroidViewModel(application) {
    var student = MutableLiveData<Student>()
    init {
        val appPreferences = AppPreferences(application.baseContext)
        student.value?.apply {
            reg_no = appPreferences.userReg
            enrolledClass = appPreferences.enrolledClass
        }
    }
}