package com.softeer.togeduck.ui.home.main

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.databinding.FragmentHomeCatergoryBinding
import com.softeer.togeduck.utils.GridSpacingItemDecoration
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.fromDpToPx


private val dummyData = listOf(
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "스포츠"),
    )

class HomeCategoryFragment : Fragment() {
    private var _binding: FragmentHomeCatergoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCatergoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = HomeCategoryAdapter(dummyData)
        val rvList = binding.rvItemCategory
        rvList.adapter = adapter
        rvList.layoutManager = GridLayoutManager(context,2)
        rvList.addItemDecoration(
            GridSpacingItemDecoration(2, 56f.fromDpToPx())
        )
        adapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_homeCategoryFragment_to_homeListFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


