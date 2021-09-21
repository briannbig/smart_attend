package app.smartattend.admin.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.databinding.FragmentEditClassBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Class
import com.google.android.material.snackbar.Snackbar

class EditClassFragment : Fragment() {

    private lateinit var binding: FragmentEditClassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditClassBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            saveClass()
        }
        return binding.root
    }

    private fun saveClass() {
        if (!TextUtils.isEmpty(binding.etClassId.text) &&
            !TextUtils.isEmpty(binding.etProgram.text)){
            FirebaseDB.classRef.child(binding.etClassId.text.toString())
                .setValue(Class(binding.etClassId.text.toString(), binding.etProgram.text.toString()))
            findNavController().navigateUp()
        }
        else Snackbar.make(requireView(), "All fields are required", Snackbar.LENGTH_SHORT).show()
    }

    companion object
}