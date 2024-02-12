package com.softeer.togeduck.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.data.model.PopularArticleModel
import com.softeer.togeduck.databinding.RvItemCategoryBinding
import com.softeer.togeduck.databinding.RvItemPopularArticleBinding

class HomeCategoryAdapter(private val items: List<HomeCategoryModel>) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    private lateinit var binding: RvItemCategoryBinding
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        binding = RvItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }
}

class CategoryViewHolder(private val binding: RvItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeCategoryModel) {
        binding.category = item
    }
}
