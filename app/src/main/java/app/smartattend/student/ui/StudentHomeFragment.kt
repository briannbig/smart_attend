package app.smartattend.student.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentStudentHomeBinding


class StudentHomeFragment : Fragment() {


    private lateinit var binding: FragmentStudentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

}