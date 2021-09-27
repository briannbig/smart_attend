package app.smartattend.admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.R
import app.smartattend.adapters.ClassAdapter
import app.smartattend.adapters.CourseAdapter
import app.smartattend.databinding.FragmentCoursesBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Class
import app.smartattend.model.Course
import com.firebase.ui.database.FirebaseRecyclerOptions

class CoursesFragment : Fragment() {

    private lateinit var binding: FragmentCoursesBinding
//    private val args: CoursesFragmentArgs by navArgs()
    private lateinit var classId: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoursesBinding.inflate(inflater, container, false)
//        classId = args.classId
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_editCourseFragment)
        }
        setUpRv()
        return binding.root
    }
    private fun setUpRv(){
        val query = FirebaseDB.courseRef.orderByValue()
        val options: FirebaseRecyclerOptions<Course> = FirebaseRecyclerOptions.Builder<Course>()
            .setQuery(query, Course::class.java).build()
        val adapter = CourseAdapter(options)
        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }

}