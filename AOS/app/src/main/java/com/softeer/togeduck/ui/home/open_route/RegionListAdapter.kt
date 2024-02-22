package com.softeer.togeduck.ui.home.open_route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.home.open_route.RegionListModel
import com.softeer.togeduck.databinding.RvItemSelectRegionBinding
import com.softeer.togeduck.utils.ItemClickWithData

class RegionListAdapter(private val items: List<RegionListModel>) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: RvItemSelectRegionBinding
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
            itemClick?.onClick(it, position, list.detailList)
        }
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