package app.smartattend.admin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.smartattend.R
import app.smartattend.databinding.FragmentClassBinding
import com.google.android.material.tabs.TabLayout

class ClassFragment : Fragment() {
    private lateinit var binding: FragmentClassBinding
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClassBinding.inflate(inflater, container, false)
        tabLayout = binding.tabLayout
        setUpTabLayout()
        return binding.root
    }

    private fun setUpTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }


}