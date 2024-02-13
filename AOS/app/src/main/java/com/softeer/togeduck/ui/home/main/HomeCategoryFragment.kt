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
import com.softeer.togeduck.utils.ItemClick


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


//////////추후 체크 필요!!!!//////////
internal class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)

        if (position >= 0) {
            val column = position % spanCount
            outRect.apply {
                left = spacing - column * spacing / spanCount
                right = (column + 1) * spacing / spanCount
                if (position < spanCount) top = spacing
                bottom = spacing
            }
        } else {
            outRect.apply {
                left = 0
                right = 0
                top = 0
                bottom = 0
            }
        }
    }
}

fun Float.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()