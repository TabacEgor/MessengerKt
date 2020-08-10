package com.example.messenger.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.messenger.databinding.ItemMessageMeBinding
import com.example.messenger.databinding.ItemMessageOtherBinding
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.ui.core.BaseAdapter

open class MessagesAdapter : BaseAdapter<MessageEntity, BaseAdapter.BaseViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)

        val holder = if (viewType == 0) {
            MessageMeViewHolder(ItemMessageMeBinding.inflate(layoutInflater, parent, false))
        } else {
            MessageOtherViewHolder(ItemMessageOtherBinding.inflate(layoutInflater, parent, false))
        }

        return holder
    }

    override fun getItemViewType(position: Int): Int {
        (getItem(position) as MessageEntity).let {
            return if (it.fromMe) 0 else 1
        }
    }

    class MessageMeViewHolder(val binding: ItemMessageMeBinding) : BaseViewHolder(binding.root) {
        init {
            binding.root.setOnLongClickListener {
                onClick?.onLongClick(item, it)

                true
            }

            binding.imgPhoto.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }

    class MessageOtherViewHolder(val binding: ItemMessageOtherBinding) : BaseAdapter.BaseViewHolder(binding.root) {
        init {
            binding.root.setOnLongClickListener {
                onClick?.onLongClick(item, it)

                true
            }

            binding.imgPhoto.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<MessageEntity>() {

        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem == newItem
        }
    }
}