package app.smartattend.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import app.smartattend.model.Class;
import app.smartattend.model.Lecturer;
import app.smartattend.model.Lesson;
import app.smartattend.model.Student;

@Dao
public interface AppDao {
    @Insert
    void insert(Lesson lesson);
    @Query("SELECT * FROM lesson")
    Lesson getLesson();
    @Query("DELETE FROM lesson")
    void deleteLesson();
    @Update
    void update(Lesson lesson);

    @Insert
    void insert(Student student);
    @Query("SELECT * FROM student")
    LiveData<Student>getStudent();
    @Delete
    void delete(Student student);
    @Update
    void update(Student student);

    @Insert
    void insert(Lecturer lecturer);
    @Query("SELECT * FROM lecturer")
    LiveData<Lecturer> getLecturer();
    @Delete
    void delete(Lecturer lecturer);
    @Update
    void update(Lecturer lecturer);
}
