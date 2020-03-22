package com.example.messenger.domain.friends

import com.example.messenger.domain.interactor.UseCase
import com.example.messenger.domain.type.None
import javax.inject.Inject

class AddFriend @Inject constructor(
    private val friendsRepository: IFriendsRepository
): UseCase<None, AddFriend.Params>() {

    override suspend fun run(params: AddFriend.Params) = friendsRepository.addFriend(params.email)

    data class Params(val email: String)
}