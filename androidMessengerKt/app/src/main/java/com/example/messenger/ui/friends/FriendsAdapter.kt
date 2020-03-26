package com.example.messenger.ui.friends

import android.view.View
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.ui.core.BaseAdapter
import kotlinx.android.synthetic.main.item_friend.view.*

class FriendsAdapter : BaseAdapter<FriendsAdapter.FriendViewHolder>() {

    override val layoutResource: Int = R.layout.item_friend

    override fun createHolder(view: View, viewType: Int): FriendsAdapter.FriendViewHolder {
        return FriendViewHolder(view)
    }

    class FriendViewHolder(view: View) : BaseViewHolder(view) {

        init {
            view.btnRemove.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any?) {
            (item as? FriendEntity)?.let {
                view.tvName.text = it.name
                view.tvStatus.text = it.status
            }
        }
    }

}