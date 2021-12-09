package app.smartattend.admin.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.adapters.ReportAdapter
import app.smartattend.admin.ui.home.ReportViewModel
import app.smartattend.databinding.FragmentCourseReportBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.ReportItem
import app.smartattend.reports.models.Report
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class CourseReportFragment : Fragment() {
    private val args : CourseReportFragmentArgs by navArgs()
    private lateinit var binding: FragmentCourseReportBinding
    private lateinit var course: Course
    private lateinit var adapter: ReportAdapter
    private lateinit var viewModel: ReportViewModel
    private var reportItems = ArrayList<ReportItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvReport.layoutManager = LinearLayoutManager(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseReportBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ReportViewModel::class.java)
        if (viewModel.course.value == null)
            CoroutineScope(Dispatchers.Main).launch { initData(args.courseId)}
        generateReport()
        binding.btnExportReport.setOnClickListener {
//            setUpRv(reportItems)
        }
        return binding.root
    }

    private fun setUpRv(courseReports: ArrayList<ReportItem>){
            Log.d("reports", "$courseReports")
            val adapter = ReportAdapter(courseReports)
            binding.rvReport.apply {
//                layoutManager = LinearLayoutManager(requireContext())
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
//                    generateReport()
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
    private fun generateReport(){
        var lessonCount = 0
        val regPool = ArrayList<String>()
        val lessonQuery = FirebaseDB.lessonRef.orderByChild("courseCode").equalTo(args.courseId)
        lessonQuery.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                   lessonCount = snapshot.childrenCount.toInt()
                    val attendeesQuery = FirebaseDB.attendanceRef.orderByChild("course").equalTo(args.courseId)
                    attendeesQuery.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                for (snap in snapshot.children){
                                    val regNo = snap.child("reg_No").value.toString()
                                    Log.d("reg--------->", regNo)
                                    regPool.add(regNo)
                                }
                                countFrequencies(regPool, lessonCount)
                                setUpRv(reportItems)
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
//        val attendeesQuery = FirebaseDB.attendanceRef.orderByChild("course").equalTo(args.courseId)
//        attendeesQuery.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if(snapshot.exists()){
//                    for (snap in snapshot.children){
//                        val regNo = snap.child("reg_No").value.toString()
//                        Log.d("reg--------->", regNo)
//                        regPool.add(regNo)
//                    }
//                    countFrequencies(regPool, lessonCount)
//                    setUpRv(reportItems)
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })
    }
    private fun countFrequencies(regPool: ArrayList<String>, lessCount: Int) {
        val hm = HashMap<String, Double>()

        for (i in regPool) {
            val j = hm[i]
            hm[i] = (if (j == null) 1.0 else j + 1.0)
        }
        for ((key, value) in hm) {
            analyzeStudPercentage(lessCount,value, key)
        }

    }
    private fun analyzeStudPercentage(count: Int, sum: Double, reg_no: String){
        val avg: Double = (sum/count) * 100
        val av_ =  "%.${1}f".format(Locale.ENGLISH,avg).toDouble()
        reportItems.add(ReportItem(reg_no, av_))
//        adapter.notifyDataSetChanged()
    }
}