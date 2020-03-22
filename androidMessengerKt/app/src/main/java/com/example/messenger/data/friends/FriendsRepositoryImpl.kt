package com.example.messenger.data.friends

import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.friends.IFriendsRepository
import com.example.messenger.domain.type.Either
import com.example.messenger.domain.type.Failure
import com.example.messenger.domain.type.None
import com.example.messenger.domain.type.flatMap

class FriendsRepositoryImpl(
    private val accountCache: IAccountCache,
    private val friendsRemote: IFriendsRemote
) : IFriendsRepository {

    override fun getFriends(): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount().flatMap { friendsRemote.getFriends(it.id, it.token) }
    }

    override fun getFriendsRequests(): Either<Failure, List<FriendEntity>> {
        return  accountCache.getCurrentAccount().flatMap { friendsRemote.getFriendRequests(it.id, it.token) }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.addFriend(email, it.id, it.token) }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.deleteFriend(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
    }
}