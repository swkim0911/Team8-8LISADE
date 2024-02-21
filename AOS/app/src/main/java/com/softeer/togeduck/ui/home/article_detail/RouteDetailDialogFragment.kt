package com.softeer.togeduck.ui.home.article_detail

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentRouteDetailBinding

class RouteDetailDialogFragment : DialogFragment() {
    private var _binding: FragmentRouteDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteDetailBinding.inflate(inflater, null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()
        init()
        dialog?.window?.setLayout(
            width,
            height
        )
        dialog?.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialogue_radius
            )
        )
    }

    private fun init() {
        binding.joinButton.setOnClickListener {
            dialog?.dismiss()
            findNavController().navigate(R.id.action_routeFragment_to_seatActivity2)
        }
        binding.iconClose.setOnClickListener{
            dialog?.dismiss()
        }
    }


}