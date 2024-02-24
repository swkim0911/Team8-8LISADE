package com.softeer.togeduck.ui.home.main.home_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.data.model.home.main.PopularArticleModel
import com.softeer.togeduck.databinding.FragmentHomeCatergoryBinding
import com.softeer.togeduck.ui.home.main.PopularArticleAdapter
import com.softeer.togeduck.utils.GridSpacingItemDecoration
import com.softeer.togeduck.utils.ItemClick
import com.softeer.togeduck.utils.fromDpToPx
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeCategoryFragment : Fragment() {
    private var _binding: FragmentHomeCatergoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeCategoryAdapter
    private val homeCategoryViewModel : HomeCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeCatergoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeCategoryViewModel.categoryList.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
    }

    private fun setupRecyclerView(data:List<HomeCategoryModel>) {
        adapter = HomeCategoryAdapter(data)
        val rvList = binding.rvItemCategory
        rvList.adapter = adapter
        rvList.layoutManager = GridLayoutManager(context, 2)
        rvList.addItemDecoration(
            GridSpacingItemDecoration(16.fromDpToPx())
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