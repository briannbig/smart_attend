package app.smartattend.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.R
import app.smartattend.adapters.AttendeeAdapter
import app.smartattend.adapters.CourseAdapter
import app.smartattend.commons.LessonViewModel
import app.smartattend.commons.ProgressManager
import app.smartattend.databinding.FragmentLessonBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Attendee
import app.smartattend.model.Course
import app.smartattend.model.Lesson
import app.smartattend.preferences.AppPreferences
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class LessonFragment : Fragment() {


    private lateinit var binding: FragmentLessonBinding
    private lateinit var lessonViewModel: LessonViewModel
    private lateinit var btmSheet: View
    private lateinit var ivQRCode: ImageView
    private lateinit var lesson: Lesson
    private lateinit var lessonRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonBinding.inflate(inflater, container, false)
        lessonViewModel = ViewModelProvider(this.requireActivity()).get(LessonViewModel::class.java)
        btmSheet = binding.root.findViewById(R.id.layout_bottomSheetBehaviorShare)
        ivQRCode = binding.root.findViewById(R.id.ivDisplayQR)
        val appPreferences = AppPreferences(requireContext())
        val startTime = appPreferences.lessonStartTime
        val endTime = appPreferences.lessonEndTime
        val code = appPreferences.lessonCourseCode
        lesson = Lesson(code, startTime, endTime)

        lessonRef = FirebaseDB.getAttendanceRef(lesson.course, lesson.startTime.toString())

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

        lessonViewModel.lesson.observe(this.requireActivity(), {
            binding.tvCourseCode.text = it.course
        })

        setUpRv()
        return binding.root
    }
    private fun setQRImage() {
            lessonRef.addValueEventListener(object: ValueEventListener{
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
    private fun setUpRv(){
        val query = FirebaseDB.lessonRef.orderByChild("course").equalTo(AppPreferences(requireContext()).lessonCourseCode)
        val options: FirebaseRecyclerOptions<Attendee> = FirebaseRecyclerOptions.Builder<Attendee>()
            .setQuery(query, Attendee::class.java).build()
        val adapter = AttendeeAdapter(options)
        binding.rvAttendees.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }
}