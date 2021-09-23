package app.smartattend.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.smartattend.model.Lecturer;
import app.smartattend.model.Lesson;
import app.smartattend.model.Student;


@Database(entities = {Student.class, Lesson.class, Lecturer.class}, version = 1)
public abstract class AppDb extends RoomDatabase {
    public abstract AppDao appDao();
    private static AppDb instance;
    private Context context;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDb getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDb.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDb.class, "mobifarm")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                AppDao dao = instance.appDao();


//                Word word = new Word("Hello");
//                dao.insert(word);
//                word = new Word("World");
//                dao.insert(word);
            });
        }
    };
}
