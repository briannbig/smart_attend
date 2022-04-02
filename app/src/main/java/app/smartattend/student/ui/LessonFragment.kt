package app.smartattend.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.R
import app.smartattend.adapters.AttendeeAdapter
import app.smartattend.commons.CalenderUtil
import app.smartattend.commons.LessonViewModel
import app.smartattend.databinding.FragmentLessonBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Attendee
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
    private lateinit var appPreferences: AppPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAttendees.layoutManager = LinearLayoutManager(requireContext())
        setUpRv()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLessonBinding.inflate(inflater, container, false)
        lessonViewModel = ViewModelProvider(this).get(LessonViewModel::class.java)
        btmSheet = binding.root.findViewById(R.id.layout_bottomSheetBehaviorShare)
        ivQRCode = binding.root.findViewById(R.id.ivDisplayQR)
        appPreferences = AppPreferences(requireContext())
        val startTime = appPreferences.lessonStartTime
        val endTime = appPreferences.lessonEndTime
        val code = appPreferences.lessonCourseCode
        lessonViewModel.lesson.observe(viewLifecycleOwner) {
            if (it == null) findNavController().navigateUp()
            lesson = it
            lessonRef = FirebaseDB.getAttendanceRef(lesson.courseCode, lesson.startTime.toString()).child("attendees")
            binding.apply {
                tvCourseCode.text = lesson.courseCode
                tvLecName.text = CalenderUtil.longToTime(lesson.endTime)
            }
            fetchLesson(lessonRef)
        }

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

//        setUpRv()
        return binding.root
    }
    private fun setQRImage() {
        val less = Lesson(
            appPreferences.lessonCourseCode, appPreferences.lessonStartTime, appPreferences.lessonEndTime
        )
            val url = Gson().toJson(less)
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 400, 400)
                ivQRCode.setImageBitmap(bitmap)
            }catch (e: Exception){}

    }
    private fun setUpRv(){
        lessonViewModel.lesson.observe(viewLifecycleOwner){
            lessonRef = FirebaseDB.getAttendanceRef(it.courseCode, it.startTime.toString()).child("attendees")
            val query = lessonRef
//          val query = FirebaseDB.lessonRef.orderByChild("course").equalTo(AppPreferences(requireContext()).lessonCourseCode)
            val options: FirebaseRecyclerOptions<Attendee> = FirebaseRecyclerOptions.Builder<Attendee>()
                .setQuery(query, Attendee::class.java).build()
            val adapter = AttendeeAdapter(options)
            binding.rvAttendees.apply {
//            layoutManager = LinearLayoutManager(requireContext())
                this.adapter = adapter
            }
            adapter.startListening()
        }
    }
    fun fetchLesson(lessonRef: DatabaseReference) {
        lessonRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    lesson.apply {
                        courseCode = snapshot.child("course").value.toString()
                        startTime = snapshot.child("startTime").value as Long?
                        endTime = snapshot.child("endTime").value as Long?
                    }
                    val courseRef = FirebaseDB.courseRef.orderByChild("code").equalTo(lesson.courseCode)
                    courseRef.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()){
                                binding.apply {
                                    tvCourseTitle.text = snapshot.child("title").value.toString()
                                    tvLecName.text = snapshot.child("lecturer").value.toString()
                                }
                            }
                        }
                        override fun onCancelled(error: DatabaseError) {}

                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun snack(message: String, length: Int = Snackbar.LENGTH_SHORT){
        Snackbar.make(requireView().rootView, message, length).show()
    }
}