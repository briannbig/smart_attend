package app.smartattend.admin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.adapters.LecturerAdapter
import app.smartattend.databinding.FragmentLecturersBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Lecturer
import com.firebase.ui.database.FirebaseRecyclerOptions

class LecturersFragment : Fragment() {

    private lateinit var binding: FragmentLecturersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLecturersBinding.inflate(inflater, container, false)
        setUpRv()
        return binding.root
    }
    private fun setUpRv(){
        val query = FirebaseDB.lecturerRef.orderByValue()
        val options: FirebaseRecyclerOptions<Lecturer> = FirebaseRecyclerOptions.Builder<Lecturer>()
            .setQuery(query, Lecturer::class.java).build()
        val adapter = LecturerAdapter(options)
        binding.rvLecturers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }

}