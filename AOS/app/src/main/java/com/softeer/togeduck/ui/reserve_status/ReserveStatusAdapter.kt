package com.softeer.togeduck.ui.reserve_status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusItemModel
import com.softeer.togeduck.data.model.reserve_status.ReserveStatusModel
import com.softeer.togeduck.databinding.RvItemReserveStatusBinding
import com.softeer.togeduck.utils.ItemClick

class ReservationStatusAdapter(private val items: ReserveStatusModel) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var binding: RvItemReserveStatusBinding
    var itemClick: ItemClick? = null

    override fun getItemCount(): Int {
        return items.reserveStatus.size
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
        holder.bind(items.reserveStatus[holder.adapterPosition])

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
    }
}

class ViewHolder(private val binding: RvItemReserveStatusBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReserveStatusItemModel) {
        binding.reserveStatus = item
    }

}
