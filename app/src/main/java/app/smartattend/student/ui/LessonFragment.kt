package app.smartattend.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.smartattend.R
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentLessonBinding
import app.smartattend.firebase.FirebaseDB
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class LessonFragment : Fragment() {


    private lateinit var binding: FragmentLessonBinding
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var btmSheet: View
    private lateinit var ivQRCode: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonBinding.inflate(inflater, container, false)
        lessonViewModel = ViewModelProvider(this.requireActivity()).get(LessonViewModel::class.java)
        btmSheet = binding.root.findViewById(R.id.layout_bottomSheetBehaviorShare)
        ivQRCode = binding.root.findViewById(R.id.ivDisplayQR)
        val bottomSheetBehavior = BottomSheetBehavior.from(btmSheet)
        binding.fabShareCode.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED){
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding.fabShareCode.visibility = View.GONE
                setQRImage()
            }
            else bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.fabShareCode.visibility = View.GONE
                    setQRImage()
                }
                else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.fabShareCode.visibility = View.VISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        lessonViewModel.getLesson()?.observe(this.requireActivity(), {
            binding.tvCourseCode.text = it.course
        })

        return binding.root
    }
    private fun setQRImage() {
        if (inProgress()){
            val lesson = lessonViewModel.getLesson()?.value!!
            val ref = FirebaseDB.getAttendanceRef(lesson.course, lesson.startTime.toString())
            ref.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        lesson.apply {
                            course = snapshot.child("course").value.toString()
                            startTime = snapshot.child("startTime").value as Long?
                            endTime = snapshot.child("endTime").value as Long?
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
            val url = Gson().toJson(lesson)
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 400, 400)
                ivQRCode.setImageBitmap(bitmap)
            }catch (e: Exception){}
        }

    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }
    private fun inProgress(): Boolean{
         return ProgressManager.checkProgress(requireContext(), lessonViewModel)
    }
}