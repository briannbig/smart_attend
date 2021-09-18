package app.smartattend.admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.databinding.FragmentClassesBinding

class ClassesFragment : Fragment() {

    private lateinit var binding: FragmentClassesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassesBinding.inflate(inflater, container, false)
        binding.fabAddClass.setOnClickListener {
            findNavController().navigate(R.id.action_classesFragment_to_editClassFragment)
        }
        return binding.root
    }

    companion object
}