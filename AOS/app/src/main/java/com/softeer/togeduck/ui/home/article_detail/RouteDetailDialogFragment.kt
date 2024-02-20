package com.softeer.togeduck.ui.home.article_detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentRouteDetailBinding

class RouteDetailDialogFragment : DialogFragment() {
    private var _binding: FragmentRouteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        _binding = FragmentRouteDetailBinding.inflate(inflater, null, false)
        val view = binding.root
        builder.setView(view)
        init()
        return builder.create()
    }

    private fun init() {
        binding.joinButton.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(R.id.action_routeFragment_to_seatActivity2)
        }
    }


}