package app.smartattend.commons

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.smartattend.db.repository.LessonRepo
import app.smartattend.model.Lesson

class LessonViewModel(application: Application) : AndroidViewModel(application){
    private val lessonRepo = LessonRepo(application)
    var lesson : LiveData<Lesson> = lessonRepo.currentLesson
    var courseCode = MutableLiveData<String>()
    var courseTitle = MutableLiveData<String>()



    fun updateLesson(lesson: Lesson) {
        lessonRepo.insert(lesson)
        this.lesson = lessonRepo.currentLesson
    }
    fun getLesson() : Lesson? {
        return lessonRepo.currentLesson.value
    }
    fun clearLesson(){
        lessonRepo.delete()
    }
    var date: String? = null
    var startTime = MutableLiveData<Long>()
    var endTime = MutableLiveData<Long>()
}