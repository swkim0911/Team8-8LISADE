package com.softeer.togeduck.ui.reserve_status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.reserve_status.ReservationStatusModel
import com.softeer.togeduck.databinding.RvItemReserveStatusBinding
import com.softeer.togeduck.utils.ItemClick


class ReservationStatusAdapter(private val items: List<ReservationStatusModel>) :
    RecyclerView.Adapter<ViewHolder>() {

    private lateinit var binding: RvItemReserveStatusBinding
    var itemClick: ItemClick? = null

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
            itemClick?.onClick(it, position)
        }
    }
}

class ViewHolder(private val binding: RvItemReserveStatusBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ReservationStatusModel) {
        binding.reserveStatus = item
    }

}
