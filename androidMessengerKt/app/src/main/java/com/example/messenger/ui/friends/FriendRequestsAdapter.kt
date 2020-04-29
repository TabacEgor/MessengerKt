package com.example.messenger.ui.friends

import android.view.View
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.ui.core.BaseAdapter
import com.example.messenger.ui.core.GlideHelper
import kotlinx.android.synthetic.main.item_friend_request.view.*

open class FriendRequestsAdapter : BaseAdapter<FriendRequestsAdapter.FriendRequestViewHolder>() {

    override val layoutResource: Int = R.layout.item_friend_request

    override fun createHolder(
        view: View,
        viewType: Int
    ): FriendRequestViewHolder {
        return FriendRequestViewHolder(view)
    }

    class FriendRequestViewHolder(view: View) : BaseViewHolder(view) {

        init {
            view.btnApprove.setOnClickListener {
                onClick?.onClick(item, it)
            }

            view.btnCancel.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any?) {
            (item as? FriendEntity)?.let {
                GlideHelper.loadImage(view.context, it.image, view.imgPhoto, R.drawable.ic_account_circle)
                view.tvName.text = it.name
            }
        }
    }

}