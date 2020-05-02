package com.example.messenger.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.messenger.R
import com.example.messenger.cache.ChatDatabase
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.presentation.viewmodel.MessagesViewModel
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseAdapter
import com.example.messenger.ui.core.BaseFragment
import com.example.messenger.ui.core.BaseListFragment
import com.example.messenger.ui.core.ext.onFailure
import com.example.messenger.ui.core.ext.onSuccess

class ChatsFragment : BaseListFragment() {

    override val viewAdapter: BaseAdapter<*> = ChatsAdapter()

    override val titleToolbar = R.string.chats

    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesViewModel = viewModel {
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }

        setOnItemClickListener { it, v ->
            (it as? MessageEntity)?.let {
                val contact = it.contact
                if (contact != null) {
                    navigator.showChatWithContact(contact.id, contact.name, requireActivity())
                }
            }
        }

        ChatDatabase.getInstance(requireContext()).messagesDao.getLiveChats().observe(this, Observer {
            val list = it.distinctBy { it.contact?.id }.toList()
            handleChats(list)
        })
    }

    override fun onResume() {
        super.onResume()

        messagesViewModel.getChats()
    }

    fun handleChats(messages: List<MessageEntity>?) {
        if (messages != null) {
            viewAdapter.clear()
            viewAdapter.add(messages)
            viewAdapter.notifyDataSetChanged()
        }
    }
}