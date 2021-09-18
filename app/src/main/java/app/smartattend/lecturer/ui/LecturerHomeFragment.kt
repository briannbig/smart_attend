package app.smartattend.lecturer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentLecturerHomeBinding

class LecturerHomeFragment : Fragment() {

    private lateinit var binding: FragmentLecturerHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentLecturerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


}