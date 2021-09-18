package app.smartattend.admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.databinding.FragmentEditCourseBinding

class EditCourseFragment : Fragment() {

    private lateinit var binding: FragmentEditCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditCourseBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            save()
        }
        return binding.root;
    }
    private fun save(){
    }

}