package com.example.messenger.remote.friends

import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.remote.core.BaseResponse

class GetFriendsResponse(
    success: Int,
    message: String,
    val friends: List<FriendEntity>
): BaseResponse(success, message)