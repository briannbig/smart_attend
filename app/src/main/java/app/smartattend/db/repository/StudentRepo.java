package app.smartattend.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import app.smartattend.db.AppDao;
import app.smartattend.db.AppDb;
import app.smartattend.model.Student;

public class StudentRepo {
    private final AppDao appDao;
    private final LiveData<Student>student;


    public StudentRepo(Application application) {
        AppDb db = AppDb.getInstance(application);
        appDao = db.appDao();
        student = appDao.getStudent();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<Student> getAllAppointments() {
        return student;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Student student) {
        AppDb.databaseWriteExecutor.execute(() -> {
            appDao.insert(student);
        });
    }
}
