package app.smartattend.commons

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.smartattend.model.Lesson

class LessonViewModel : ViewModel(){
    private var lesson = MutableLiveData<Lesson>()
    var courseCode = MutableLiveData<String>()
    var courseTitle = MutableLiveData<String>()

    fun updateLesson(lesson: Lesson) {
        this.lesson.value = lesson
    }
    fun getLesson() : MutableLiveData<Lesson> {
        return lesson
    }
    var date: String? = null
    var startTime = MutableLiveData<Long>()
    var endTime = MutableLiveData<Long>()
}