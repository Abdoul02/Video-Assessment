package com.abdoul.videoassessment.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.abdoul.videoassessment.domain.model.BaseVideoItem

class BaseDiffUtil(
    private val oldList: List<BaseVideoItem>,
    private val newList: List<BaseVideoItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                false
            }
            oldList[oldItemPosition].subtitle != newList[newItemPosition].subtitle -> {
                false
            }
            oldList[oldItemPosition].date != newList[newItemPosition].date -> {
                false
            }
            oldList[oldItemPosition].imageUrl != newList[newItemPosition].imageUrl -> {
                false
            }
            else -> true
        }
    }
}