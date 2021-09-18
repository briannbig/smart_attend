package app.smartattend.student.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentLessonBinding

class LessonFragment : Fragment() {


    private lateinit var binding: FragmentLessonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonBinding.inflate(inflater, container, false)
        return binding.root
    }
    
}