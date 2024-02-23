package com.softeer.togeduck.ui.reserve_status

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.softeer.togeduck.databinding.FragmentReserveStatusBinding
import com.softeer.togeduck.utils.ItemClickWithRouteId
import com.softeer.togeduck.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReserveStatusFragment : Fragment() {
    private var _binding: FragmentReserveStatusBinding? = null
    private val binding get() = _binding!!
    private val reserveStatusViewModel: ReserveStatusViewModel by viewModels()
    private lateinit var adapter: ReservationStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReserveStatusBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun init() {
        reserveStatusViewModel.loadReserveStatusData()

        reserveStatusViewModel.reserveStatusItems.observe(viewLifecycleOwner) {
            adapter = ReservationStatusAdapter(it)
            val rvList = binding.rvReservationStatusList
            val dividerItemDecoration =
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            rvList.addItemDecoration(dividerItemDecoration)
            rvList.adapter = adapter

            adapter.itemClick = object : ItemClickWithRouteId {
                override fun onClick(view: View, position: Int, routeId: Int) {
                    val action =
                        ReserveStatusFragmentDirections.actionMenuReserveListToReservationStatusDetailActivity(
                            routeId
                        )
                    findNavController().navigate(action)
                }
            }
        }

        reserveStatusViewModel.errMessage.observe(viewLifecycleOwner) {
            showErrorToast(requireContext(), it.toString())
        }
    }

}
