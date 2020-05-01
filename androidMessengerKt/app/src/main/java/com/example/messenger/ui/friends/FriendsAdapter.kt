package com.example.messenger.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.example.messenger.R
import com.example.messenger.databinding.ItemFriendBinding
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.ui.core.BaseAdapter
import com.example.messenger.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override fun createHolder(parent: ViewGroup): FriendsAdapter.FriendViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendBinding.inflate(layoutInflater, parent, false)
        return FriendViewHolder(binding)
    }

    class FriendViewHolder(val binding: ItemFriendBinding) : BaseViewHolder(binding.root) {

        init {
            binding.btnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any?) {
            (item as? FriendEntity)?.let {
                binding.friend = it
            }
        }
    }
}