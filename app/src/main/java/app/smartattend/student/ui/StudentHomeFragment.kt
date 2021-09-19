package app.smartattend.student.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentStudentHomeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator


class StudentHomeFragment : Fragment() {


    private lateinit var binding: FragmentStudentHomeBinding
    internal var qrScanIntegrator: IntentIntegrator? =null

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
        binding.buttonAttend.setOnClickListener {
            initiateScan()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result!=null){
            if (result.contents == null){
                snack("Results not found")
            }
        }
    }
    private fun initiateScan() {
        qrScanIntegrator?.initiateScan()
    }

    private fun attend() {

    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
//        Snackbar.make()
    }
}