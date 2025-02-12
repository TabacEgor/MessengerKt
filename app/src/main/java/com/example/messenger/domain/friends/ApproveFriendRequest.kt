package com.example.messenger.domain.friends

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.None
import javax.inject.Inject

class ApproveFriendRequest @Inject constructor(
    private val friendsRepository: IFriendsRepository
): UseCase<None, FriendEntity>() {

    override suspend fun run(params: FriendEntity) = friendsRepository.approveFriendRequest(params)
}