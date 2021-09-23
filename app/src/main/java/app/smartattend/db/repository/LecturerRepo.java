package app.smartattend.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import app.smartattend.db.AppDao;
import app.smartattend.db.AppDb;
import app.smartattend.model.Lecturer;
import app.smartattend.model.Student;

public class LecturerRepo {
    private final AppDao appDao;
    private final LiveData<Lecturer> lecturer;


    public LecturerRepo(Application application) {
        AppDb db = AppDb.getInstance(application);
        appDao = db.appDao();
        lecturer = appDao.getLecturer();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<Lecturer> getAllAppointments() {
        return lecturer;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Lecturer lecturer) {
        AppDb.databaseWriteExecutor.execute(() -> {
            appDao.insert(lecturer);
        });
    }
}
