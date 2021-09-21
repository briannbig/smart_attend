package app.smartattend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import app.smartattend.admin.AdminActivity
import app.smartattend.databinding.ActivityMainBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.lecturer.LecturerActivity
import app.smartattend.model.User
import app.smartattend.preferences.AppPreferences
import app.smartattend.student.StudentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

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
            binding.button2.setOnClickListener {
                initiateLogin()
            }
        }
    }

    private fun initiateLogin() {
        if (!TextUtils.isEmpty(binding.etUsername.text) && !TextUtils.isEmpty(binding.etPswd.text)){
            val username: String = binding.etUsername.text.toString()
            val password: String = binding.etPswd.text.toString()
            val query = FirebaseDB.userRef.orderByChild("username").equalTo(username)
            query.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        Log.d("query", " $snapshot")
                        val uname: String = snapshot.child(username).child("username").value.toString()
                        val pswd: String = snapshot.child(username).child("password").value.toString()
                        val usertype: String = snapshot.child(username).child("userType").value.toString()
                        val user = User(uname, pswd, usertype)
                        Log.d("User---->:", "${user.username}, ${user.password}, ${user.userType}")
                        if (user.password == password){
                            AppPreferences(applicationContext).apply {
                                userType = user.userType
                                logged_in = true
                                loadApp()
                            }
                        }
                    }
                    else
                        makeSnack("user not found")
                }
                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
                }
            })
        }
        else
            makeSnack("All Fields Are required")
    }

    private fun loadApp() {
        when {
            AppPreferences(this).userType.equals("admin") -> {
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
            AppPreferences(this).userType.equals("lecturer")-> {
                startActivity(Intent(this, LecturerActivity::class.java))
                finish()
            }
            AppPreferences(this).userType.equals("student") -> {
                startActivity(Intent(this, StudentActivity::class.java))
                finish()
            }
        }
    }
    private fun makeSnack(message: String, duration: Int = 2){
//        Snackbar.make()
    }
}