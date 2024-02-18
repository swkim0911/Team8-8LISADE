package com.softeer.togeduck.ui.home.article_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.RouteListModel
import com.softeer.togeduck.databinding.FragmentRouteBinding
import com.softeer.togeduck.ui.home.article_detail.RouteDetailDialogFragment
import com.softeer.togeduck.ui.home.open_route.RegionListDialog
import com.softeer.togeduck.ui.reserve_status.ReservationStatusAdapter
import com.softeer.togeduck.utils.ItemClick


private val dummyData = listOf(
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
    RouteListModel("2024.02.17 / 09:00 출발", "부산시청 앞", "10,000", 30, 24, "모집중"),
)

class RouteFragment : Fragment() {

    private lateinit var adapter: RouteListAdapter
    private var _binding: FragmentRouteBinding? = null
    private val binding get() = _binding!!
    private val routeViewModel: RouteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpArrayAdapter()
        init()
        makePopUp()
        binding.makeRouteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_routeFragment_to_openRouteActivity)
        }

    }

    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.article_detail_sort_list)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_category_sort_list, regionArray)
        binding.listSortMenu.adapter = arrayAdapter
        binding.vm = routeViewModel
    }

    private fun init() {
        adapter = RouteListAdapter(dummyData)
        val rvList = binding.rvRouteList
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(context)
        rvList.addItemDecoration(dividerItemDecoration)
        adapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                RouteDetailDialogFragment().show(parentFragmentManager, "RouteDetailDialogFragment")
            }
        }

    }

    private fun makePopUp() {
        binding.selectStartRegion.setOnClickListener {
            val popup = PopupMenu(requireContext(), it)
            popup.menuInflater.inflate(R.menu.start_region_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                }
                true
            }
            popup.show()
            true
        }
    }

//    private fun showDialog(){
//        val dialog = RouteDetailDialogFragment()
//        dialog.show(parentFragmentManager. )
//    }
}