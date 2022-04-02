package app.smartattend.student.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressListener
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentStudentHomeBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Attendance
import app.smartattend.model.Lesson
import app.smartattend.preferences.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*


class StudentHomeFragment : Fragment() {


    private lateinit var binding: FragmentStudentHomeBinding
    private var qrScanIntegrator: IntentIntegrator? =null
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var studentViewModel: StudentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false)
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this).apply {
            setOrientationLocked(true)
            setPrompt("Scan bar code to mark attendance")
            setBeepEnabled(true)
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setTimeout(10000)
            setCameraId(0)
        }
        lessonViewModel = ViewModelProvider(this.requireActivity()).get(LessonViewModel::class.java)
        studentViewModel = ViewModelProvider(this).get(StudentViewModel::class.java)
        binding.card3.setOnClickListener {
            if (ProgressManager.inProgress(requireContext())) {
                findNavController().navigate(R.id.action_navigation_student_home_to_lessonFragment)
            }
            else initiateScan()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result!=null){
            if (result.contents == null){
            }
            else{
                Log.d("QR-results", result.contents)
                attend(result.contents)
            }
        }
    }
    private fun initiateScan() {
        qrScanIntegrator?.initiateScan()
    }

    private fun attend(qrCodeContent: String) {
        try {
            if (Gson().fromJson(qrCodeContent, Lesson::class.java) != null) {
                val lesson: Lesson = Gson().fromJson(qrCodeContent, Lesson::class.java)
                if (inTime(lesson.startTime, lesson.endTime)) {
                    if (belongsToClass(lesson.courseCode)){
                        val attendanceRef =
                            FirebaseDB.getAttendanceRef(lesson.courseCode, lesson.startTime.toString()).child("attendees")
                        val cal =  Calendar.getInstance().timeInMillis
                        attendanceRef.apply {
                            child(AppPreferences(requireContext()).userReg!!).child("reg_No").setValue(AppPreferences(requireContext()).userReg)
                            child(AppPreferences(requireContext()).userReg!!).child("time_In").setValue(cal)
                        }

                        val attendeesRef = FirebaseDB.attendanceRef
                        attendeesRef.apply {
                            push().setValue(Attendance(AppPreferences(requireContext()).userReg,
                                lesson.courseCode
                            ))
                        }

                        lessonViewModel.apply {
                            courseCode.value = lesson.courseCode
                            startTime.value = lesson.startTime
                            endTime.value = lessonViewModel.endTime.value
                        }
                        lessonViewModel.updateLesson(lesson)
                        AppPreferences(requireContext()).apply {
                            lessonCourseCode = lesson.courseCode
                            lessonStartTime = lesson.startTime
                            lessonEndTime = lesson.endTime
                        }
                        ProgressListener().onProgressStart(requireContext(), lesson.endTime)
                        snack("success")
                    }
                    else
                        snack("Not a member of this class!!")
                }
                else
                    snack("Lesson not in Progress now")
                }
            else
                snack("Invalid QR Code")
        }
        catch (jse: JsonSyntaxException){
            snack("Invalid QR Code")
        }
    }

    private fun belongsToClass(courseCode: String): Boolean {
        return true
    }

    private fun inTime(startTime: Long, endTime: Long): Boolean {
        val currentTime: Long = Calendar.getInstance().timeInMillis
        return currentTime in startTime..endTime
    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }
}