package com.softeer.togeduck.ui.reserve_status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.databinding.RvItemReserveStatusBinding
import com.softeer.togeduck.utils.ItemClickWithRouteId

class ReservationStatusAdapter :
    PagingDataAdapter<ReserveStatusItemModel, ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReserveStatusItemModel>() {
            override fun areItemsTheSame(
                oldItem: ReserveStatusItemModel,
                newItem: ReserveStatusItemModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ReserveStatusItemModel,
                newItem: ReserveStatusItemModel,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    private lateinit var binding: RvItemReserveStatusBinding
    var itemClick: ItemClickWithRouteId? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemReserveStatusBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)

            holder.itemView.setOnClickListener {
                itemClick?.onClick(it, position, item.id)
            }
        }
    }
}

class ViewHolder(private val binding: RvItemReserveStatusBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveStatusItemModel) {
        binding.reserveStatus = item
    }

}
