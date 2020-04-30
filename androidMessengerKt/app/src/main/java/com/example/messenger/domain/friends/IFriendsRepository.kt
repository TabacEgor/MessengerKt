package com.example.messenger.domain.friends

import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None

interface IFriendsRepository {

    fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>>

    fun getFriendsRequests(needFetch: Boolean): Either<Failure, List<FriendEntity>>

    fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None>

    fun addFriend(email: String): Either<Failure, None>

    fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None>
}