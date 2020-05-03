package com.example.messenger.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.messenger.databinding.ItemChatBinding
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.ui.core.BaseAdapter

open class ChatsAdapter : MessagesAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemChatBinding.inflate(layoutInflater, parent, false)
        return ChatViewHolder(binding)
    }

    class ChatViewHolder(val binding: ItemChatBinding) : BaseAdapter.BaseViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick?.onClick(item, it)
            }
        }

        override fun onBind(item: Any) {
            (item as? MessageEntity)?.let {
                binding.message = it
            }
        }
    }
}