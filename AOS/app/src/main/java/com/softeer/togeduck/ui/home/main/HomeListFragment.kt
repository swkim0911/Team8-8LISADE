package com.softeer.togeduck.ui.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.databinding.FragmentHomeListBinding
import com.softeer.togeduck.ui.home.PopularArticleAdapter
import com.softeer.togeduck.utils.ItemClick



private val dummyData = listOf(
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "뮤지컬"),
    HomeCategoryModel("dummy", "콘서트"),
    HomeCategoryModel("dummy", "팬미팅"),
)

class HomeListFragment : Fragment() {
    private var _binding: FragmentHomeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeListCategoryChipAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArrayAdapter()
        init()
    }

    private fun initArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.category_sort_list)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.item_category_sort_list, regionArray)
        binding.listSortMenu.adapter = arrayAdapter

    }

    private fun init() {
        binding.rvCategoryList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeListCategoryChipAdapter(dummyData)
        }
//        adapter.itemClick = object : ItemClick {
//            override fun onClick(view: View, position: Int) {
//                findNavController().navigate(R.id.action_homeCategoryFragment_to_homeListFragment)
//            }
//        }

    }

}