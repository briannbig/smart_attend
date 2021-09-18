package app.smartattend.lecturer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import app.smartattend.R
import app.smartattend.databinding.ActivityLecturerBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class LecturerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLecturerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLecturerBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_lecturer)

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_lecturer_home, R.id.navigation_notifications)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}