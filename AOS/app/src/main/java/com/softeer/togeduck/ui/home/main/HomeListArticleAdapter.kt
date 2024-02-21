package com.softeer.togeduck.ui.home.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softeer.togeduck.data.model.HomeArticleModel
import com.softeer.togeduck.databinding.RvItemArticleBinding
import com.softeer.togeduck.utils.ItemClick

class HomeListArticleAdapter(private val items: List<HomeArticleModel>) :
    RecyclerView.Adapter<ArticleViewHolder>() {
    private lateinit var binding: RvItemArticleBinding

    var itemClick: ItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        binding = RvItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }

    }
}

class ArticleViewHolder(private val binding: RvItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeArticleModel) {
        binding.homeArticle = item
    }
}