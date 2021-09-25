package app.smartattend.admin.ui

import android.R
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.smartattend.commons.ClassViewModel
import app.smartattend.databinding.FragmentEditCourseBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Course
import app.smartattend.model.Lecturer
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class EditCourseFragment : Fragment() {

    private lateinit var binding: FragmentEditCourseBinding
    private lateinit var classViewModel: ClassViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditCourseBinding.inflate(inflater, container, false)
        classViewModel = ViewModelProvider(requireActivity()).get(ClassViewModel::class.java)
        classViewModel.classId.observe(requireActivity(), {
            binding.tvClassId.text = it
        })
        binding.button.setOnClickListener {
            saveCourse()
        }
        setUpSpinner()
        return binding.root;
    }
    private fun saveCourse(){
        if (!TextUtils.isEmpty(binding.etCode.text) &&
            !TextUtils.isEmpty(binding.etTitle.text) &&
            binding.spinerLecturers.count!=0){
                val lecturer: Lecturer = binding.spinerLecturers.selectedItem as Lecturer
                FirebaseDB.courseRef.child(binding.etCode.text.toString())
                        .setValue(Course(binding.etCode.text.toString(), binding.etTitle.text.toString(),
                            lecturer.lecNo, classViewModel.classId.value.toString() ))
                findNavController().navigateUp()
        }
        else Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT).show()
    }
    private fun setUpSpinner(){
        val query = FirebaseDB.lecturerRef.orderByChild("lecturer")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val lecturers = arrayListOf<Lecturer>()
                    for (i in snapshot.children){
                        val lecturer = Lecturer(i.child("lecNo").value.toString(), i.child("name").value.toString())
                        lecturers.add(lecturer)
                        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, lecturers)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinerLecturers.adapter = adapter
                        Log.d("courses", "$i")
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}