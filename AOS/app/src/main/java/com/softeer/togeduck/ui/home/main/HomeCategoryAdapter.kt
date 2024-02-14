package com.softeer.togeduck.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.HomeCategoryModel
import com.softeer.togeduck.databinding.RvItemCategoryBinding
import com.softeer.togeduck.utils.ItemClick

class HomeCategoryAdapter(private val items: List<HomeCategoryModel>) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    private lateinit var binding: RvItemCategoryBinding
    var itemClick: ItemClick? = null
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
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }
}

class CategoryViewHolder(private val binding: RvItemCategoryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeCategoryModel) {
        binding.category = item
    }
}