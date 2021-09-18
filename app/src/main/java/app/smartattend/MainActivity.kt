package app.smartattend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import app.smartattend.admin.AdminActivity
import app.smartattend.databinding.ActivityMainBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.lecturer.LecturerActivity
import app.smartattend.model.User
import app.smartattend.preferences.AppPreferences
import app.smartattend.student.StudentActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppPreferences(this).logged_in){
            loadApp()
        }
        else{
            loadApp()
//            binding.button2.setOnClickListener {
//                initiateLogin()
//            }
        }
    }

    private fun initiateLogin() {
        if (!TextUtils.isEmpty(binding.etUsername.text) && !TextUtils.isEmpty(binding.etPswd.text)){
            val username: String = binding.etUsername.text.toString()
            val password: String = binding.etPswd.text.toString()
            if (FirebaseDB.insertUser(User(username, password))){
                loadApp()
            }
            else
                makeSnack("Could not log in")
        }
        else
            makeSnack("All Fields Are required")
    }

    private fun loadApp() {
        when {
            AppPreferences(this).isAdmin -> {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
            AppPreferences(this).isLecturer -> {
                startActivity(Intent(this, LecturerActivity::class.java))
                finish()
            }
            else -> {
                startActivity(Intent(this, StudentActivity::class.java))
                finish()
            }
        }
    }
    private fun makeSnack(message: String, duration: Int = 2){
//        Snackbar.make()
    }
}