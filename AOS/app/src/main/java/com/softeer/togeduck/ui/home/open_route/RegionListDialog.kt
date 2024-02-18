package com.softeer.togeduck.ui.home.open_route

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.data.model.RegionDetailModel
import com.softeer.togeduck.data.model.RegionListModel
import com.softeer.togeduck.databinding.DialogSelectRegionBinding
import com.softeer.togeduck.utils.ItemClickWithData

private val dummyData = listOf(
    RegionListModel(
        "서울",
        listOf(RegionDetailModel("1"), RegionDetailModel("2"), RegionDetailModel("3"))
    ),
    RegionListModel(
        "부산",
        listOf(RegionDetailModel("4"), RegionDetailModel("5"), RegionDetailModel("6"))
    ),
    RegionListModel(
        "대전",
        listOf(RegionDetailModel("1"), RegionDetailModel("2"), RegionDetailModel("3"))
    )
)

class RegionListDialog : DialogFragment() {

    private var _binding: DialogSelectRegionBinding? = null
    private val binding get() = _binding!!

    private lateinit var regionListAdapter: RegionListAdapter
    private var regionDetailListAdapter: RegionDetailListAdapter? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        _binding = DialogSelectRegionBinding.inflate(inflater, null, false)
        val view = binding.root

        regionListAdapter = RegionListAdapter(dummyData)

        binding.regionList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regionListAdapter
        }

        regionDetailListAdapter = RegionDetailListAdapter(emptyList())
        binding.placeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = regionDetailListAdapter
        }

        regionListAdapter.itemClick = object : ItemClickWithData {
            override fun onClick(view: View, position: Int, detailList: List<RegionDetailModel>) {
                regionDetailListAdapter = RegionDetailListAdapter(detailList).also {
                    binding.placeList.adapter = it
                }
            }

        }
        builder.setView(view)
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}