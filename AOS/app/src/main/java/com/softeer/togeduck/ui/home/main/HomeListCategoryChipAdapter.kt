package com.softeer.togeduck.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.databinding.RvItemCategoryChipBinding
import com.softeer.togeduck.utils.ItemClick
class HomeListCategoryChipAdapter(private val items: List<HomeCategoryModel>) :
    RecyclerView.Adapter<CategoryChipViewHolder>() {
    private lateinit var binding: RvItemCategoryChipBinding
    var itemClick: ItemClick? = null
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryChipViewHolder {
        binding = RvItemCategoryChipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryChipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryChipViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }
}

class CategoryChipViewHolder(private val binding: RvItemCategoryChipBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeCategoryModel) {
        binding.categoryChip = item
    }
}


