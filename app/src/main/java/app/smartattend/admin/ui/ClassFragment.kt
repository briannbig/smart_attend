package app.smartattend.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.R
import app.smartattend.adapters.ClassAdapter
import app.smartattend.adapters.CourseAdapter
import app.smartattend.adapters.StudentAdapter
import app.smartattend.commons.ClassViewModel
import app.smartattend.databinding.FragmentClassBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Class
import app.smartattend.model.Course
import app.smartattend.model.Student
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DatabaseReference

class ClassFragment : Fragment() {
    private lateinit var binding: FragmentClassBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var coursesRef: DatabaseReference
    private lateinit var studentsRef: DatabaseReference
    private lateinit var classViewModel: ClassViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassBinding.inflate(inflater, container, false)
        classViewModel = ViewModelProvider(requireActivity()).get(ClassViewModel::class.java)
        tabLayout = binding.tabLayout

        coursesRef = FirebaseDB.courseRef
        studentsRef = FirebaseDB.studentRef
        setUpTabLayout()
        setUpCoursesRv()
        binding.btnAddCourse.setOnClickListener {
            classViewModel.classId.value = "DICT-2018-S"
            findNavController().navigate(R.id.action_classFragment_to_editCourseFragment)
        }
        binding.btnAddStudent.setOnClickListener {
            classViewModel.classId.value = "DICT-2018-S"
            findNavController().navigate(R.id.action_classFragment_to_editStudentFragment)
        }
        return binding.root
    }

    private fun setUpTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab){
                    binding.tabLayout.getTabAt(0) -> setUpCoursesRv()
                    binding.tabLayout.getTabAt(1) -> setUpStudentsRv()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun setUpCoursesRv(){
        val query = coursesRef.orderByChild("classId").equalTo(classViewModel.classId.value)
        val options: FirebaseRecyclerOptions<Course> = FirebaseRecyclerOptions.Builder<Course>()
            .setQuery(query, Course::class.java).build()
        val adapter = CourseAdapter(options)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }
    private fun setUpStudentsRv(){
        val query = studentsRef.orderByChild("enrolledClass").equalTo(classViewModel.classId.value)
        val options: FirebaseRecyclerOptions<Student> = FirebaseRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java).build()
        val adapter = StudentAdapter(options)
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }



}