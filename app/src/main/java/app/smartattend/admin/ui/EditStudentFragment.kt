package app.smartattend.admin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.commons.ClassViewModel
import app.smartattend.databinding.FragmentEditStudentBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Student


class EditStudentFragment : Fragment() {
    private lateinit var binding: FragmentEditStudentBinding
    private lateinit var classViewModel: ClassViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        classViewModel = ViewModelProvider(requireActivity()).get(ClassViewModel::class.java)
        classViewModel.classId.observe(requireActivity(), {
            binding.tvClassId.text = it
        })
        binding.btnSaveStudent.setOnClickListener { save() }
        return binding.root
    }
    private fun save(){
        if (!TextUtils.isEmpty(binding.etName.text)
            && !TextUtils.isEmpty(binding.etRegNo.text)){
                val student = Student(binding.etRegNo.text.toString(), binding.etName.text.toString(),
                        classViewModel.classId.value)
            FirebaseDB.studentRef.child(student.reg_no).setValue(student)
            findNavController().navigateUp()
        }
    }

}