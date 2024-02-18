package com.softeer.togeduck.ui.home.open_route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.RegionDetailModel
import com.softeer.togeduck.databinding.RvItemSelectRegionBinding
import com.softeer.togeduck.databinding.RvItemSelectRegionDetailBinding

class RegionDetailListAdapter(private val items: List<RegionDetailModel>) :
    RecyclerView.Adapter<DetailViewHolder>() {
    private lateinit var binding: RvItemSelectRegionDetailBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        binding = RvItemSelectRegionDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }
}

class DetailViewHolder(private val binding: RvItemSelectRegionDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RegionDetailModel) {
        binding.detail = item
    }
}