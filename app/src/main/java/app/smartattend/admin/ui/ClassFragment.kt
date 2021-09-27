package app.smartattend.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ClassFragment : Fragment() {

    val args: ClassFragmentArgs by navArgs()

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

        initData(args.classId)
        coursesRef = FirebaseDB.courseRef
        studentsRef = FirebaseDB.studentRef
        setUpTabLayout()
        setUpCoursesRv()
        binding.btnAddCourse.setOnClickListener {
            findNavController().navigate(R.id.action_classFragment_to_editCourseFragment)
        }
        binding.btnAddStudent.setOnClickListener {
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
    private fun initData(classId: String){
        val query = FirebaseDB.classRef.orderByChild("id").equalTo(classId)
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val regNo: String = snapshot.child(classId).child("id").value.toString()
                    val program: String = snapshot.child(classId).child("program").value.toString()
                    val classs = Class(regNo, program)
                    classViewModel.updateClass(classs)
                    binding.apply {
                        tvCourseCode.text = regNo
                        tvProgram.text = program
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }


}