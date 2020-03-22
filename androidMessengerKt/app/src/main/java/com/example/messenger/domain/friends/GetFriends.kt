package com.example.messenger.domain.friends

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.None
import javax.inject.Inject

class GetFriends @Inject constructor(
    private val friendsRepository: IFriendsRepository
): UseCase<List<FriendEntity>, None>() {

    override suspend fun run(params: None) = friendsRepository.getFriends()
}