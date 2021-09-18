package app.smartattend.admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentEditClassBinding

class EditClassFragment : Fragment() {

    private lateinit var binding: FragmentEditClassBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditClassBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object
}