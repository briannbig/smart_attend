package app.smartattend.admin.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.smartattend.R
import app.smartattend.adapters.ClassAdapter
import app.smartattend.databinding.FragmentClassesBinding
import app.smartattend.firebase.FirebaseDB
import app.smartattend.model.Class
import com.firebase.ui.database.FirebaseRecyclerOptions

class ClassesFragment : Fragment() {

    private lateinit var binding: FragmentClassesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassesBinding.inflate(inflater, container, false)
        binding.fabAddClass.setOnClickListener {
            findNavController().navigate(R.id.action_classesFragment_to_editClassFragment)
        }
        setUpRv()
        return binding.root
    }

    private fun setUpRv(){
        val query = FirebaseDB.classRef.orderByValue()
        val options: FirebaseRecyclerOptions<Class> = FirebaseRecyclerOptions.Builder<Class>()
            .setQuery(query, Class::class.java).build()
        val adapter = ClassAdapter(options)
        binding.rvClasses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        adapter.startListening()
    }
}