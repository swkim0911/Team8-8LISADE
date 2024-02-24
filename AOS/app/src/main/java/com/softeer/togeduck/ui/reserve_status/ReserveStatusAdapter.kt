package com.softeer.togeduck.ui.reserve_status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.databinding.RvItemReserveStatusBinding
import com.softeer.togeduck.utils.ItemClickWithRouteId

class ReservationStatusAdapter(private val items: List<ReserveStatusItemModel>) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var binding: RvItemReserveStatusBinding
    var itemClick: ItemClickWithRouteId? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemReserveStatusBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position, items[holder.adapterPosition].id)
        }
    }
}

class ViewHolder(private val binding: RvItemReserveStatusBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveStatusItemModel) {
        binding.reserveStatus = item
    }

}
