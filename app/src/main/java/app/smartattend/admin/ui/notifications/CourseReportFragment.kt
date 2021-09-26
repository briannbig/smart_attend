package app.smartattend.admin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.adapters.ReportAdapter
import app.smartattend.commons.CourseViewModel
import app.smartattend.databinding.FragmentCourseReportBinding
import app.smartattend.reports.ReportUtil


class CourseReportFragment : Fragment() {
    private lateinit var binding: FragmentCourseReportBinding
    private lateinit var courseViewModel: CourseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCourseReportBinding.inflate(inflater, container, false)
        courseViewModel = ViewModelProvider(requireActivity()).get(CourseViewModel::class.java)
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

}