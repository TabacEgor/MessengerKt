package com.example.messenger.ui.friends

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.type.None
import com.example.messenger.presentation.viewmodel.FriendsViewModel
import com.example.messenger.ui.App
import com.example.messenger.ui.core.BaseAdapter
import com.example.messenger.ui.core.BaseListFragment
import com.example.messenger.ui.core.ext.onFailure
import com.example.messenger.ui.core.ext.onSuccess

class FriendsFragment : BaseListFragment() {

    override val viewAdapter: BaseAdapter<*> = FriendsAdapter()
    override val titleToolbar: Int = R.string.screen_friends

    lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        friendsViewModel = viewModel {
            onSuccess(friendsData, ::handleFriends)
            onSuccess(deleteFriendData, ::handleDeleteFriend)
            onSuccess(progressData, ::updateProgress)
            onFailure(failureData, ::handleFailure)
        }

        setOnItemClickListener { it, v ->
            (it as? FriendEntity)?.let {
                when (v.id) {
                    R.id.btnRemove -> showDeleteFriendDialog(it)
                    else -> {
                        navigator.showUser(requireActivity(), it)
                    }
                }
            }
        }

        friendsViewModel.getFriends()
    }

    private fun showDeleteFriendDialog(friendEntity: FriendEntity) {
        activity?.let {
            AlertDialog.Builder(it)
                .setMessage(getString(R.string.delete_friend))
                .setPositiveButton(android.R.string.yes) {
                    dialog, which -> friendsViewModel.deleteFriend(friendEntity)
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    private fun handleFriends(friends: List<FriendEntity>?) {
        hideProgress()
        if (friends != null) {
            viewAdapter.clear()
            viewAdapter.add(friends)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleDeleteFriend(none: None?) {
        friendsViewModel.getFriends()
    }
}