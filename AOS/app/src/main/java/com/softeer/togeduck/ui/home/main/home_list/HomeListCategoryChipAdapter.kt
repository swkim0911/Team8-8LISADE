package com.softeer.togeduck.ui.home.main.home_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.home.main.HomeCategoryModel
import com.softeer.togeduck.databinding.RvItemCategoryChipBinding
import com.softeer.togeduck.utils.ItemClickWithCategoryId

class HomeListCategoryChipAdapter(
    private val items: List<HomeCategoryModel>,
    private val categoryId: Int
) :
    RecyclerView.Adapter<CategoryChipViewHolder>() {
    private lateinit var binding: RvItemCategoryChipBinding
    private var selectedItemPosition = categoryId
    var itemClick: ItemClickWithCategoryId? = null

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
            val previousSelectedPosition = selectedItemPosition
            selectedItemPosition = position
            if (previousSelectedPosition >= 0) {
                notifyItemChanged(previousSelectedPosition)
            }
            notifyItemChanged(position)
            itemClick?.onClick(it, position,selectedItemPosition)

        }
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundResource(R.drawable.item_home_category_chip_clicked)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.item_home_category_chip)
        }
    }
}

class CategoryChipViewHolder(private val binding: RvItemCategoryChipBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeCategoryModel) {
        binding.categoryChip = item
    }
}


