package com.softeer.togeduck.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.data.model.PopularArticleModel
import com.softeer.togeduck.databinding.FragmentHomeBinding
import com.softeer.togeduck.databinding.FragmentHomeCatergoryBinding


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
        binding.rvItemCategory.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = HomeCategoryAdapter(dummyData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}