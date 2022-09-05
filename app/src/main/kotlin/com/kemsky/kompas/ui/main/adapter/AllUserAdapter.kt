package com.kemsky.kompas.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kemsky.kompas.data.model.UserModel
import com.kemsky.kompas.databinding.ItemUserBinding
import com.kemsky.kompas.helper.loadImage
import com.kemsky.kompas.ui.detail.DetailActivity

class AllUserAdapter :
    PagingDataAdapter<UserModel, AllUserAdapter.AllUserViewHolder>(
        AllUserComparator
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllUserViewHolder {
        return AllUserViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AllUserViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPokemon(it) }
    }

    inner class AllUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPokemon(item: UserModel) = with(binding) {
            imgUser.loadImage(item.avatarUrl ?: "-")
            txtUser.text = item.login

            this.root.setOnClickListener { view ->
                Intent(view.context, DetailActivity::class.java).also {
                    it.putExtra("username", item.login)
                    view.context.startActivity(it)
                }
            }
        }
    }

    object AllUserComparator : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(
            oldItem: UserModel,
            newItem: UserModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserModel,
            newItem: UserModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}