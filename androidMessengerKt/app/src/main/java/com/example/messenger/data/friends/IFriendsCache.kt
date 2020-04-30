package com.example.messenger.data.friends

import com.example.messenger.domain.friends.FriendEntity

interface IFriendsCache {

    fun saveFriend(entity: FriendEntity)

    fun getFriend(key: Long): FriendEntity?

    fun getFriends(): List<FriendEntity>

    fun getFriendRequests(): List<FriendEntity>

    fun removeFriendEntity(key: Long)
}