package app.smartattend.admin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.adapters.ReportAdapter
import app.smartattend.commons.CourseViewModel
import app.smartattend.databinding.FragmentCourseReportBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Class
import app.smartattend.model.Course
import app.smartattend.reports.ReportUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class CourseReportFragment : Fragment() {
    private val args : CourseReportFragmentArgs by navArgs()
    private lateinit var binding: FragmentCourseReportBinding
    private lateinit var courseViewModel: CourseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseReportBinding.inflate(inflater, container, false)
        courseViewModel = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
        initData(args.courseId)
        courseViewModel.course.observe(viewLifecycleOwner,{
            binding.tvCourseCode.text = it.code
            binding.tvCourseTitle.text = it.title
        })

        setUpRv()
        return binding.root
    }

    private fun setUpRv(){
        val reports = courseViewModel.course.value?.let { ReportUtil().analyzeForSpecificCourse(it) }
        val adapter = reports?.let { ReportAdapter(it) }
        binding.rvReport.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }
    private fun initData(courseCode: String){
        val query = FirebaseDB.courseRef.orderByChild("code").equalTo(courseCode)
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val code: String = snapshot.child(courseCode).child("code").value.toString()
                    val title: String = snapshot.child(courseCode).child("title").value.toString()
                    val lecturer: String = snapshot.child(courseCode).child("lecturer").value.toString()
                    val classId: String = snapshot.child(courseCode).child("classId").value.toString()
                    val course = Course(code, title, lecturer, classId)
                    courseViewModel.course.value = course
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }

}