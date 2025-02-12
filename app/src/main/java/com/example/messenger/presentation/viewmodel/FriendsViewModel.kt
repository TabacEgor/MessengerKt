package com.example.messenger.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.messenger.domain.friends.*
import com.example.messenger.domain.type.None
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    val getFriendsUseCase: GetFriends,
    val deleteFriendsUseCase: DeleteFriend,
    val addFriendUseCase: AddFriend,
    val getFriendRequestsUseCase: GetFriendRequests,
    val approveFriendRequestUseCase: ApproveFriendRequest,
    val cancelFriendRequestUseCase: CancelFriendRequest
) : BaseViewModel() {

    var friendsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    var friendRequestsData: MutableLiveData<List<FriendEntity>> = MutableLiveData()
    var deleteFriendData: MutableLiveData<None> = MutableLiveData()
    var addFriendData: MutableLiveData<None> = MutableLiveData()
    var approveFriendData: MutableLiveData<None> = MutableLiveData()
    var cancelFriendData: MutableLiveData<None> = MutableLiveData()

    fun getFriends(needFetch: Boolean = false) {
        getFriendRequestsUseCase(needFetch) { it.either(::handleFailure) { handleFriends(it, !needFetch) } }
    }

    fun getFriendRequests(needFetch: Boolean = false) {
        getFriendRequestsUseCase(needFetch) { it.either(::handleFailure) { handleFriendRequests(it, !needFetch) } }
    }

    fun deleteFriend(friendEntity: FriendEntity) {
        deleteFriendsUseCase(friendEntity) { it.either(::handleFailure, ::handleDeleteFriend) }
    }

    fun addFriend(email: String) {
        addFriendUseCase(AddFriend.Params(email)) { it.either(::handleFailure, ::handleAddFriend) }
    }

    fun approveFriend(friendEntity: FriendEntity) {
        approveFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleApproveFriend) }
    }

    fun cancelFriend(friendEntity: FriendEntity) {
        cancelFriendRequestUseCase(friendEntity) { it.either(::handleFailure, ::handleCancelFriend) }
    }

    private fun handleFriends(friends: List<FriendEntity>, fromCache: Boolean) {
        friendsData.value = friends
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFriends(true)
        }
    }

    private fun handleFriendRequests(friends: List<FriendEntity>, fromCache: Boolean) {
        friendRequestsData.value = friends
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFriendRequests(true)
        }
    }

    private fun handleDeleteFriend(none: None?) {
        deleteFriendData.value = none
    }

    private fun handleAddFriend(none: None?) {
        addFriendData.value = none
    }

    private fun handleApproveFriend(none: None?) {
        approveFriendData.value = none
    }

    private fun handleCancelFriend(none: None?) {
        cancelFriendData.value = none
    }

    override fun onCleared() {
        super.onCleared()
        getFriendsUseCase.unsubscribe()
        getFriendRequestsUseCase.unsubscribe()
        deleteFriendsUseCase.unsubscribe()
        addFriendUseCase.unsubscribe()
        approveFriendRequestUseCase.unsubscribe()
        cancelFriendRequestUseCase.unsubscribe()
    }
}