package com.example.messenger.data.friends

import com.example.messenger.data.account.IAccountCache
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.friends.IFriendsRepository
import com.example.messenger.domain.type.*

class FriendsRepositoryImpl(
    private val accountCache: IAccountCache,
    private val friendsRemote: IFriendsRemote,
    private val friendsCache: IFriendsCache
) : IFriendsRepository {

    override fun getFriends(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount()
            .flatMap {
                return@flatMap if (needFetch) {
                    friendsRemote.getFriends(it.id, it.token)
                } else {
                    Either.Right(friendsCache.getFriends())
                }
            }
            .onNext { it.map { friendsCache.saveFriend(it) } }
    }

    override fun getFriendsRequests(needFetch: Boolean): Either<Failure, List<FriendEntity>> {
        return accountCache.getCurrentAccount()
            .flatMap {
                return@flatMap if (needFetch) {
                    friendsRemote.getFriendRequests(it.id, it.token)
                } else {
                    Either.Right(friendsCache.getFriendRequests())
                }
            }
            .onNext { it.map {
                it.isRequest = 1
                friendsCache.saveFriend(it)
            } }
    }

    override fun approveFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.approveFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext {
                friendEntity.isRequest = 1
                friendsCache.saveFriend(friendEntity)
            }
    }

    override fun cancelFriendRequest(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.cancelFriendRequest(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext { friendsCache.removeFriendEntity(friendEntity.id) }
    }

    override fun addFriend(email: String): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.addFriend(email, it.id, it.token) }
    }

    override fun deleteFriend(friendEntity: FriendEntity): Either<Failure, None> {
        return accountCache.getCurrentAccount()
            .flatMap { friendsRemote.deleteFriend(it.id, friendEntity.id, friendEntity.friendsId, it.token) }
            .onNext { friendsCache.removeFriendEntity(friendEntity.id) }
    }
}