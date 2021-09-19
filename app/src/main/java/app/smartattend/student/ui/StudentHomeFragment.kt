package app.smartattend.student.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.smartattend.R
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentStudentHomeBinding
import app.smartattend.model.Lesson
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.zxing.integration.android.IntentIntegrator


class StudentHomeFragment : Fragment() {


    private lateinit var binding: FragmentStudentHomeBinding
    private var qrScanIntegrator: IntentIntegrator? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentStudentHomeBinding.inflate(inflater, container, false)
        qrScanIntegrator = IntentIntegrator.forSupportFragment(this).apply {
            setOrientationLocked(true)
            setPrompt("Scan bar code to sign in a lesson/class")
            setBeepEnabled(true)
            setCameraId(0)
        }
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
                snack("Results not found")
            }
            else{
                attend(result.contents)
            }
        }
    }
    private fun initiateScan() {
        qrScanIntegrator?.initiateScan()
    }

    private fun attend(qrCodeContent: String) {
        try {
            if (Gson().fromJson(qrCodeContent, Lesson::class.java) != null){
                val lesson: Lesson = Gson().fromJson(qrCodeContent, Lesson::class.java)
                ProgressManager.startProgress(requireContext())
            }
            else{
                snack("Invalid QR Code")
            }
        }
        catch (jse: JsonSyntaxException){
            snack("Invalid QR Code")
        }
    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }
}