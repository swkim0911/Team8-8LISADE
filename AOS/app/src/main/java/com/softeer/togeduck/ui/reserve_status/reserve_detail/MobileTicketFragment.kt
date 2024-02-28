package com.softeer.togeduck.ui.reserve_status.reserve_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.softeer.togeduck.R
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.softeer.togeduck.databinding.FragmentMobileTicketBinding
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MobileTicketFragment : Fragment() {
    private var _binding: FragmentMobileTicketBinding? = null
    private val binding get() = _binding!!
    private val mobileTicketViewModel: MobileTicketViewModel by viewModels()
    private val args: MobileTicketFragmentArgs by navArgs()

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
                R.layout.fragment_mobile_ticket,
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
        binding.vm = mobileTicketViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val routeId = args.routeId
        mobileTicketViewModel.routeId = routeId

        mobileTicketViewModel.loadMobileTicketData()
        binding.seatingChartBtn.setOnClickListener {
            val routeId = mobileTicketViewModel.routeId
            val action =
                MobileTicketFragmentDirections.actionMobileTicketFragmentToSeatChartFragmentDialogue(
                    routeId
                )
            findNavController().navigate(action)
        }

        mobileTicketViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }

        makeQR()
    }

    private fun makeQR() {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val data =
                "routeId=" + mobileTicketViewModel.routeId + ";seatNo" + mobileTicketViewModel.mobileTicket.value?.seatNo + ";"
            val bitmap =
                barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400)
            val imageViewQrCode = binding.qrImage
            imageViewQrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Toast.makeText(context, "입장권 QR코드 생성에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}