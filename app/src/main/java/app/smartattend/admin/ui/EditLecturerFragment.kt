package app.smartattend.admin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.smartattend.databinding.FragmentEditLecturerBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Lecturer
import app.smartattend.model.Student
import app.smartattend.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class EditLecturerFragment : Fragment() {
    private val args: EditLecturerFragmentArgs by navArgs()
    private lateinit var binding: FragmentEditLecturerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditLecturerBinding.inflate(inflater, container, false)
        if (!args.lecRegNo.isNullOrBlank()){
            initData(args.lecRegNo!!)
        }
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
    private fun initData(lecNo: String){
        val query = FirebaseDB.lecturerRef.orderByChild("lecNo").equalTo(lecNo)
        query.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val regNo: String = snapshot.child(lecNo).child("lecNo").value.toString()
                    val name: String = snapshot.child(lecNo).child("name").value.toString()
                    binding.apply{
                        tvClassId.text = "Edit Lecturer"
                        etRegNo.setText(regNo)
                        etRegNo.isEnabled = false
                        etName.setText(name)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

        })
    }

}