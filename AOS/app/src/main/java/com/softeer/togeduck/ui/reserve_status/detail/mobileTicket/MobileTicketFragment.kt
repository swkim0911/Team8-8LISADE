package com.softeer.togeduck.ui.reserve_status.detail.mobileTicket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.softeer.togeduck.databinding.FragmentMobileTicketBinding


class MobileTicketFragment : Fragment() {
    private var _binding: FragmentMobileTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                com.softeer.togeduck.R.layout.fragment_mobile_ticket,
                container,
                false
            )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seatingChartBtn.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialogFragment = SeatChartFragmentDialogue()
        dialogFragment.show(parentFragmentManager,"SeatChartFragmentDialogue")
    }
}