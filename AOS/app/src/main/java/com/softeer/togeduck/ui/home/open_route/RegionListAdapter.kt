package com.softeer.togeduck.ui.home.open_route

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.R
import com.softeer.togeduck.data.model.RegionDetailModel
import com.softeer.togeduck.data.model.RegionItem
import com.softeer.togeduck.data.model.RegionListModel
import com.softeer.togeduck.databinding.RvItemPopularArticleBinding
import com.softeer.togeduck.databinding.RvItemSelectRegionBinding
import com.softeer.togeduck.utils.ItemClickWithData

class RegionListAdapter(private val items: List<RegionListModel>) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: RvItemSelectRegionBinding
    private var selectedItemPosition = RecyclerView.NO_POSITION
    var itemClick: ItemClickWithData? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemSelectRegionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = items[holder.adapterPosition]
        holder.bind(list)
        holder.itemView.setOnClickListener{
            val previousSelectedPosition = selectedItemPosition
            itemClick?.onClick(it, position, list.detailList)
            selectedItemPosition = position
            if (previousSelectedPosition >= 0) {
                notifyItemChanged(previousSelectedPosition)
            }
            notifyItemChanged(position)
        }
//        if (position == selectedItemPosition) {
//            holder.itemView.setBackgroundResource(R.drawable.item_home_category_chip_clicked)
//        } else {
//            holder.itemView.setBackgroundResource(R.drawable.)
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder(private val binding: RvItemSelectRegionBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RegionListModel) {
        binding.detail = item
    }
}