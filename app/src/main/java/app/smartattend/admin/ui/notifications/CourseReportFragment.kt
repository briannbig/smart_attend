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
import app.smartattend.admin.ui.home.ReportViewModel
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CourseReportFragment : Fragment() {
    private val args : CourseReportFragmentArgs by navArgs()
    private lateinit var binding: FragmentCourseReportBinding
    private lateinit var course: Course
    private lateinit var adapter: ReportAdapter
    private lateinit var viewModel: ReportViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseReportBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        if (viewModel.course.value == null)
            CoroutineScope(Dispatchers.Main).launch { initData(args.courseId)}

        return binding.root
    }

    private fun setUpRv(courseReports: ArrayList<ReportItem>){
            Log.d("reports", "$courseReports")
            val adapter = ReportAdapter(courseReports)
            binding.rvReport.apply {
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                this.adapter = adapter
            }
    }
    private suspend fun initData(courseCode: String){
        val query = FirebaseDB.courseRef.orderByChild("code").equalTo(courseCode)
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val code: String = snapshot.child(courseCode).child("code").value.toString()
                    val title: String = snapshot.child(courseCode).child("title").value.toString()
                    val lecturer: String = snapshot.child(courseCode).child("lecturer").value.toString()
                    val classId: String = snapshot.child(courseCode).child("classId").value.toString()
                    course = Course(code, title, lecturer, classId)
                    viewModel.course.value = course
                    binding.apply {
                        tvCourseCode.text = course.code
                        tvCourseTitle.text= course.title
                    }
                    CoroutineScope(Dispatchers.Main).launch {
                        val reports = viewModel.fetch()

                        if (reports != null) {
                            setUpRv(reports)
                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }
        })
    }
}