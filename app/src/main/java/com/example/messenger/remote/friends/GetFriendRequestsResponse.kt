package com.example.messenger.remote.friends

import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.remote.core.BaseResponse
import com.google.gson.annotations.SerializedName

class GetFriendRequestsResponse(
    success: Int,
    message: String,
    @SerializedName("friend_requests")
    val friendRequests: List<FriendEntity>
) : BaseResponse(success, message)