package com.example.messenger.ui.friends

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.messenger.databinding.ItemFriendBinding
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.ui.core.BaseAdapter

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.FriendViewHolder {
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

        override fun onBind(item: Any) {
            (item as? FriendEntity)?.let {
                binding.friend = it
            }
        }
    }
}