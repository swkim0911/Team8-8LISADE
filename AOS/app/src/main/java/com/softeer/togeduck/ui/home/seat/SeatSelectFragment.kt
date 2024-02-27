package com.softeer.togeduck.ui.home.seat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.softeer.togeduck.R
import com.softeer.togeduck.databinding.FragmentSeatSelectBinding
import com.softeer.togeduck.ui.custom_view.SeatCustomView
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatSelectFragment : Fragment() {
    private var _binding: FragmentSeatSelectBinding? = null
    private val binding get() = _binding!!
    private val seatViewModel: SeatViewModel by activityViewModels()
    private var selectedSeatNum: Int = -1

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
        binding.vm = seatViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        seatViewModel.loadSeatsInfoData()
        seatViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }

        binding.selectCompleteBtn.setOnClickListener {
            val routeId = seatViewModel.routeId
            val action = SeatSelectFragmentDirections.actionSeatSelectFragmentToSeatPaymentFragment(
                routeId, selectedSeatNum
            )
            findNavController().navigate(action)
        }

        binding.seatSelectView.itemClick = object : SeatCustomView.ItemClickWithSeat {
            override fun onClick(existSelectedSeat: Boolean, existBeforeSelected: Boolean) {
                binding.selectCompleteBtn.isEnabled = existSelectedSeat
                if (existSelectedSeat) { // 좌석 클릭됐을때 다시 누름
                    binding.selectCompleteBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.main
                        )
                    )
                    selectedSeatNum = binding.seatSelectView.selectedSeatNum
                    binding.totalPriceValue.text = seatViewModel.seatsInfo.value?.formattedPrice
                    if (!existBeforeSelected) {
                        binding.seatLeftoverValue.text =
                            (binding.seatLeftoverValue.text.toString().toInt() - 1).toString()
                    }
                } else {
                    binding.selectCompleteBtn.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.gray300
                        )
                    )
                    selectedSeatNum = binding.seatSelectView.selectedSeatNum
                    binding.totalPriceValue.text = "0"
                    binding.seatLeftoverValue.text =
                        (binding.seatLeftoverValue.text.toString().toInt() + 1).toString()
                }

            }
        }
    }

}