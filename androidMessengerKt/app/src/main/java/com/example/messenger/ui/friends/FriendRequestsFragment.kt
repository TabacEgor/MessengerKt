package com.example.messenger.ui.friends

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

class FriendRequestsFragment : BaseListFragment() {

    override val viewAdapter: BaseAdapter<*> = FriendRequestsAdapter()
    override val layoutId: Int = R.layout.inner_fragment_list

    lateinit var friendsViewModel: FriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        base {
            friendsViewModel = viewModel {
                onSuccess(friendRequestsData, ::handleFriendRequests)
                onSuccess(approveFriendData, ::handleFriendRequestAction)
                onSuccess(cancelFriendData, ::handleFriendRequestAction)
                onFailure(failureData, ::handleFailure)
            }
        }

        setOnItemClickListener { item, v ->
            (item as? FriendEntity)?.let {
                when (v.id) {
                    R.id.btnApprove -> {
                        showProgress()
                        friendsViewModel.approveFriend(it)
                    }
                    R.id.btnCancel -> {
                        showProgress()
                        friendsViewModel.cancelFriend(it)
                    }
                    else -> {
                        activity?.let { act ->
                            navigator.showUser(act, it)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showProgress()
        friendsViewModel.getFriendRequests()
    }

    private fun handleFriendRequests(requests: List<FriendEntity>?) {
        hideProgress()
        if (requests != null) {
            viewAdapter.clear()
            viewAdapter.add(requests)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun handleFriendRequestAction(none: None?) {
        hideProgress()
        friendsViewModel.getFriendRequests()
    }
}