package com.softeer.togeduck.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.databinding.RvItemPopularArticleBinding
import com.softeer.togeduck.data.model.PopularArticleModel

class PopularArticleAdapter(private val items: List<PopularArticleModel>) :
    RecyclerView.Adapter<ViewHolder>() {
    private lateinit var binding: RvItemPopularArticleBinding
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemPopularArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }
}

class ViewHolder(private val binding: RvItemPopularArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PopularArticleModel) {
        binding.popularArticle = item
    }
}