package com.example.messenger.data.friends

import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None

interface IFriendsRemote {

    fun getFriends(userId: Long, token: String): Either<Failure, List<FriendEntity>>

    fun getFriendRequests(userId: Long, token: String): Either<Failure, List<FriendEntity>>

    fun approveFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>

    fun cancelFriendRequest(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>

    fun addFriend(email: String, userId: Long, token: String): Either<Failure, None>

    fun deleteFriend(userId: Long, requestUserId: Long, friendsId: Long, token: String): Either<Failure, None>
}