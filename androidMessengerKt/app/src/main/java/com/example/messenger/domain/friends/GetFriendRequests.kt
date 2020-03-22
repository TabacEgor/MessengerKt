package com.example.messenger.domain.friends

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.None
import javax.inject.Inject

class GetFriendRequests @Inject constructor(
    private val friendRepository: IFriendsRepository
): UseCase<List<FriendEntity>, None>() {

    override suspend fun run(params: None) = friendRepository.getFriendsRequests()
}