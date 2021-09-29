package app.smartattend.admin.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.adapters.ReportAdapter
import app.smartattend.commons.CourseViewModel
import app.smartattend.databinding.FragmentCourseReportBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import app.smartattend.reports.CallBackImpl
import app.smartattend.reports.ReportGen
import app.smartattend.reports.ReportUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CourseReportFragment : Fragment() {
    private val args : CourseReportFragmentArgs by navArgs()
    private lateinit var binding: FragmentCourseReportBinding
    private lateinit var course: Course
    private lateinit var callBackImpl: CallBackImpl
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseReportBinding.inflate(inflater, container, false)
        callBackImpl = CallBackImpl()
        initData(args.courseId)
//        setUpRv(course)

        return binding.root
    }

    private fun setUpRv(courseReports: ArrayList<ReportItem>){
//            val reports = ReportGen().courseReport
            Log.d("reports", "$courseReports")
            val adapter = ReportAdapter(courseReports)
            binding.rvReport.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
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
                    course = Course(code, title, lecturer, classId)
                    binding.apply {
                        tvCourseCode.text = course.code
                        tvCourseTitle.text= course.title
                    }
                    ReportGen().analyzeForSpecificCourse(callBackImpl,course)
                }
                setUpRv(callBackImpl.onComplete())
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }

}