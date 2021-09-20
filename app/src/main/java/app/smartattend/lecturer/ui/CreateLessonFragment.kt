package app.smartattend.lecturer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentCreateLessonBinding
import app.smartattend.model.Lesson
import com.google.android.material.snackbar.Snackbar
import java.util.*


class CreateLessonFragment : Fragment() {
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var binding: FragmentCreateLessonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateLessonBinding.inflate(inflater, container, false)
        lessonViewModel = ViewModelProvider(this.requireActivity()).get(LessonViewModel::class.java)
        binding.tvEndTime.setOnClickListener {
            TimePickerFragment(binding.tvEndTime, lessonViewModel).show(requireActivity().supportFragmentManager, "time picker")
        }
        binding.buttonOkay.setOnClickListener {
            startSession()
        }
        return binding.root
    }

    private fun startSession(){
        if (binding.tvEndTime.text != "end time"){
            lessonViewModel.getLesson().value = Lesson("Sample Course", Calendar.getInstance().timeInMillis,
            lessonViewModel.endTime.value)
            ProgressManager.startProgress(requireContext())
            snack("success")
            findNavController().popBackStack()
        }else
            snack("Select end time first!")
    }
    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }

}