package app.smartattend.admin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.databinding.FragmentEditLecturerBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Lecturer
import app.smartattend.model.Student
import app.smartattend.model.User


class EditLecturerFragment : Fragment() {
    private lateinit var binding: FragmentEditLecturerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditLecturerBinding.inflate(inflater, container, false)
        binding.btnSaveStudent.setOnClickListener { save() }
        return binding.root
    }
    private fun save(){
        if (!TextUtils.isEmpty(binding.etName.text)
            && !TextUtils.isEmpty(binding.etRegNo.text)){
            val lecturer = Lecturer(binding.etRegNo.text.toString(), binding.etName.text.toString())
            FirebaseDB.lecturerRef.child(lecturer.lecNo).setValue(lecturer)
            FirebaseDB.userRef.child(lecturer.lecNo).setValue(User(lecturer.lecNo,lecturer.lecNo, "lecturer"))
            findNavController().navigateUp()
        }
    }
}