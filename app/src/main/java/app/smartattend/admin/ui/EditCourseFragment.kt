package app.smartattend.admin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.databinding.FragmentEditCourseBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import com.google.android.material.snackbar.Snackbar

class EditCourseFragment : Fragment() {

    private lateinit var binding: FragmentEditCourseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditCourseBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            saveCourse()
        }
        return binding.root;
    }
    private fun saveCourse(){
        if (!TextUtils.isEmpty(binding.etCode.text) &&
            !TextUtils.isEmpty(binding.etTitle.text) &&
            !TextUtils.isEmpty(binding.etLec.text) &&
            !TextUtils.isEmpty(binding.etClassId.text)){
            FirebaseDB.courseRef.child(binding.etCode.text.toString())
                .setValue(Course(binding.etCode.text.toString(), binding.etTitle.text.toString(),
                binding.etLec.text.toString(), binding.etClassId.text.toString()))
            findNavController().navigateUp()
        }
        else Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT).show()
    }

}