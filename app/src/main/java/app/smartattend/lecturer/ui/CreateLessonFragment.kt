package app.smartattend.lecturer.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentCreateLessonBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.Lesson
import app.smartattend.preferences.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import java.util.*


class CreateLessonFragment : Fragment() {
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var binding: FragmentCreateLessonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateLessonBinding.inflate(inflater, container, false)
        lessonViewModel = ViewModelProvider(this.requireActivity()).get(LessonViewModel::class.java)
        binding.button3.setOnClickListener {
            TimePickerFragment(binding.tvEndTime, lessonViewModel).show(requireActivity().supportFragmentManager, "time picker")
        }
        binding.buttonOkay.setOnClickListener {
            startSession()
        }
        setUpSpinner()
        return binding.root
    }

    private fun startSession(){
        if (binding.tvEndTime.text != "time not set"){
            val course: Course = binding.spinnerCourses.selectedItem as Course
            lessonViewModel.getLesson().value = Lesson(course.code, Calendar.getInstance().timeInMillis,
            lessonViewModel.endTime.value)
            ProgressManager.startProgress(requireContext(), lessonViewModel.endTime.value!!)
            snack("success")
            findNavController().popBackStack()
        }else
            snack("Select end time first!")
    }
    private fun setUpSpinner(){
        val query = FirebaseDB.courseRef.orderByChild("lecturer").equalTo(getLec())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val courses = arrayListOf<Course>()
                    for (i in snapshot.children){
                        val course = Course(i.child("code").value.toString(), i.child("title").value.toString(),
                            i.child("lecturer").value.toString(), i.child("title").value.toString())
                        courses.add(course)
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courses)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerCourses.adapter = adapter
                        Log.d("courses", "$i")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getLec(): String {
        return AppPreferences(requireContext()).userReg!!
    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }

}