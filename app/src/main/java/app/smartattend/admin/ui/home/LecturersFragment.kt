package app.smartattend.admin.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentLecturersBinding

class LecturersFragment : Fragment() {

    private lateinit var binding: FragmentLecturersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLecturersBinding.inflate(inflater, container, false)
        return binding.root
    }

}