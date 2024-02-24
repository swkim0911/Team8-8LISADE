package com.softeer.togeduck.ui.home.seat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatSelectBinding
import com.softeer.togeduck.utils.ItemClickWithSeat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatSelectFragment : Fragment() {
    private var _binding: FragmentSeatSelectBinding? = null
    private val binding get() = _binding!!
    private val seatSelectViewModel: SeatSelectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_seat_select, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.selectCompleteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_seatSelectFragment_to_seatPaymentFragment)
        }
        binding.seatSelectView.itemClick = object : ItemClickWithSeat {
            override fun onClick(existSelectedSeat: Boolean) {
                binding.selectCompleteBtn.isEnabled = existSelectedSeat
                if (existSelectedSeat) {
                    binding.selectCompleteBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.main
                        )
                    )
                } else {
                    binding.selectCompleteBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.gray300
                        )
                    )
                }

            }
        }

    }
}