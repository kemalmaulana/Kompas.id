package com.kemsky.kompas.ui.detail.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kemsky.kompas.data.model.GithubRepoModel
import com.kemsky.kompas.databinding.ItemRepoBinding
import java.text.SimpleDateFormat
import java.util.*

class RepoAdapter :
    ListAdapter<GithubRepoModel, RepoAdapter.RepoViewHolder>(
        DiffCallback
    ) {

    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bindRepo(pokemon)
    }

    inner class RepoViewHolder(private var binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindRepo(item: GithubRepoModel) = with(binding) {

            txtRepoDesc.text = item.description ?: "No Description"
            txtRepoName.text = item.name ?: "-"
            txtStarNumber.text = item.stargazersCount?.toString() ?: "0"

            sdf.apply {
                isLenient = true
                timeZone = TimeZone.getTimeZone("GMT")
            }

            val time = sdf.parse(item.updatedAt.toString())?.time
            val now = System.currentTimeMillis()
            val ago = time?.let { DateUtils.getRelativeTimeSpanString(it, now, DateUtils.WEEK_IN_MILLIS) }
            txtUpdated.text = "Updated $ago"

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GithubRepoModel>() {
        override fun areItemsTheSame(
            oldItem: GithubRepoModel,
            newItem: GithubRepoModel
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: GithubRepoModel,
            newItem: GithubRepoModel
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }

}