package app.smartattend.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import app.smartattend.db.AppDao;
import app.smartattend.db.AppDb;
import app.smartattend.model.Lecturer;
import app.smartattend.model.Lesson;
import app.smartattend.model.Student;

public class LessonRepo {
    private final AppDao appDao;
    private final LiveData<Lesson> lesson;


    public LessonRepo(Application application) {
        AppDb db = AppDb.getInstance(application);
        appDao = db.appDao();
        lesson = appDao.getLesson();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<Lesson> getAllAppointments() {
        return lesson;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Lesson lesson) {
        AppDb.databaseWriteExecutor.execute(() -> {
            appDao.insert(lesson);
        });
    }
}
