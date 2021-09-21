package app.smartattend.admin.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.smartattend.MainActivity
import app.smartattend.R
import app.smartattend.databinding.FragmentNotificationsBinding
import app.smartattend.preferences.AppPreferences
import com.google.firebase.auth.FirebaseAuth

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.cvLogout.setOnClickListener { logout() }
        return binding.root
    }

    private fun logout() {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }
        builder?.apply {
            setTitle("You are about to log out")
            setMessage("Proceed?")
            setPositiveButton("Yes") { _, _ ->
                AppPreferences(requireContext()).preferences.edit().clear().apply()
                activity?.apply {
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    finish()
                }
            }
            setNegativeButton("No"){_, _ -> }
        }
        builder?.setMessage("Proceed?")
            ?.setTitle("You are about to log out")
        val dialog: AlertDialog? = builder?.create()
        dialog?.show()
    }
}