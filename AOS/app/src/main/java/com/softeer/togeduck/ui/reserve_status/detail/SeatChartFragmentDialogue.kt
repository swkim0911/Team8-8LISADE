package com.softeer.togeduck.ui.reserve_status.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatChartDialogueBinding


class SeatChartFragmentDialogue : DialogFragment() {
    private var _binding: FragmentSeatChartDialogueBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_seat_chart_dialogue,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()

        val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.9).toInt()

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


}