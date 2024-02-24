package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.softeer.togeduck.databinding.FragmentMobileTicketBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MobileTicketFragment : Fragment() {
    private var _binding: FragmentMobileTicketBinding? = null
    private val binding get() = _binding!!
    private val reserveDetailViewModel: ReserveStatusDetailViewModel by activityViewModels()

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

        init()
    }

    private fun init() {
        reserveDetailViewModel.loadMobileTicketData()
        binding.seatingChartBtn.setOnClickListener {
            showDialog()
        }


    }

    private fun showDialog() {
        val dialogFragment = SeatChartFragmentDialogue()
        dialogFragment.show(parentFragmentManager, "SeatChartFragmentDialogue")
    }
}