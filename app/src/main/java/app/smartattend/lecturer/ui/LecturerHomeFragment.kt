package app.smartattend.lecturer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentLecturerHomeBinding

class LecturerHomeFragment : Fragment() {

    private lateinit var binding: FragmentLecturerHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentLecturerHomeBinding.inflate(inflater, container, false)
        binding.card2.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_lecturer_home_to_coursesFragment3)
        }
        binding.card3.setOnClickListener {
            if (ProgressManager.inProgress(requireContext()))
                findNavController().navigate(R.id.action_navigation_lecturer_home_to_navigation_lesson_fragment)
            else
                findNavController().navigate(R.id.action_navigation_lecturer_home_to_createLessonFragment)
        }
        return binding.root
    }


}