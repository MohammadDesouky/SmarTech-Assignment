package com.smarTech.assignment.main.view.reposRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smarTech.assignment.R
import com.smarTech.assignment.constants.AppConstants
import com.smarTech.assignment.main.model.GitHubRepo
import kotlinx.android.synthetic.main.repo_item_card.view.*

class GitHubReposPagedAdapter :
    PagedListAdapter<GitHubRepo, GitHubRepoViewHolder>(reposComparator) {

    companion object {

        private val reposComparator = object : DiffUtil.ItemCallback<GitHubRepo>() {
            override fun areItemsTheSame(firstRepo: GitHubRepo, secondRepo: GitHubRepo): Boolean {
                return firstRepo.apiId == secondRepo.apiId
            }

            override fun areContentsTheSame(
                firstRepo: GitHubRepo,
                secondRepo: GitHubRepo
            ): Boolean {
                return firstRepo.fullName == secondRepo.fullName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GitHubRepoViewHolder(parent)

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        holder.bindRepoToView(position, getItem(position))
    }

}


class GitHubRepoViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.repo_item_card, parent, false)
) {


    fun bindRepoToView(
        repoIndex: Int,
        repo: GitHubRepo?
    ) {
        val prefix = if (AppConstants.SHOW_ITEM_NUMBER) "${repoIndex+1}- " else ""
        val name = "$prefix${repo?.name}"
        itemView.itemNameTextView.text = name
        itemView.itemDescriptionTextView.text = repo?.description ?: "{NULL}"
        if (repo?.isPrivate == false) {
            val context = itemView.context
            itemView.itemNameTextView.background.setTint(
                ContextCompat.getColor(
                    context,
                    R.color.colorPrimaryDark
                )
            )
        }
    }
}