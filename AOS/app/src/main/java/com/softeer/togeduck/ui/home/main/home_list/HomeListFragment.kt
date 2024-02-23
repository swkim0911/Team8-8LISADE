package com.softeer.togeduck.ui.home.main.home_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.main.HomeArticleModel
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.databinding.FragmentHomeListBinding
import com.softeer.togeduck.utils.ItemClick


private val dummyData = listOf(
    HomeCategoryModel("dummy", "스포츠"),
    HomeCategoryModel("dummy", "뮤지컬"),
    HomeCategoryModel("dummy", "콘서트"),
    HomeCategoryModel("dummy", "팬미팅"),
    HomeCategoryModel("dummy", "애니메이션"),
    HomeCategoryModel("dummy", "기타"),
)

private val articleDummyData = listOf(
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02"),
    HomeArticleModel("dummy", "[서울] 싸이 흠뻑쇼", "잠실 종합운동장 올림픽주경기장", "2023.06.30", "2023.07.02")
)

class HomeListFragment : Fragment() {
    private var _binding: FragmentHomeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: HomeListCategoryChipAdapter
    private lateinit var articleAdapter: HomeListArticleAdapter
    private val homeListViewModel: HomeListViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        homeListViewModel.festivalList.observe(viewLifecycleOwner, Observer {
            setUpRvArticleListRecyclerView(it)
        })
//        getArticleSize()
    }


    private fun init() {
        setUpRvCategoryRecyclerView(dummyData)
        setUpArrayAdapter()
        binding.vm = homeListViewModel
    }

    private fun getArticleSize() {
        homeListViewModel.setItemSize(articleAdapter.itemCount.toString())
    }

    private fun setUpRvCategoryRecyclerView(data: List<HomeCategoryModel>) {
        val rvCategoryList = binding.rvCategoryList
        categoryAdapter = HomeListCategoryChipAdapter(data)
        rvCategoryList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        categoryAdapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
            }
        }
    }

    private fun setUpArrayAdapter() {
        val regionArray = resources.getStringArray(R.array.category_sort_list)
        val arrayAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_category_sort_list,
                R.id.textView,
                regionArray
            )
        binding.listSortMenu.adapter = arrayAdapter
    }

    private fun setUpRvArticleListRecyclerView(data: List<HomeArticleModel>) {
        articleAdapter = HomeListArticleAdapter(data)
        val rvArticleList = binding.rvArticleList
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val rvList = binding.rvArticleList
        rvArticleList.addItemDecoration(dividerItemDecoration)
        rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter
        }
        articleAdapter.itemClick = object : ItemClick {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_homeListFragment_to_articleDetailActivity)
            }
        }

    }
}