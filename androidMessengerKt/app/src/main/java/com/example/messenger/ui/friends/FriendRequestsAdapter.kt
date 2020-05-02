package com.example.messenger.ui.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.example.messenger.R
import com.example.messenger.databinding.ActivityNavigationBinding.inflate
import com.example.messenger.databinding.ItemFriendRequestBinding
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.ui.core.BaseAdapter
import com.example.messenger.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend_request.view.*

open class FriendRequestsAdapter : BaseAdapter<FriendRequestsAdapter.FriendRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendRequestBinding.inflate(layoutInflater)
        return FriendRequestViewHolder(binding)
    }

    class FriendRequestViewHolder(val binding: ItemFriendRequestBinding) : BaseViewHolder(binding.root) {

        init {
            binding.btnApprove.setOnClickListener {
                onClick?.onClick(item, it)
            }

            binding.btnCancel.setOnClickListener {
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