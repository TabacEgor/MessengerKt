package com.example.messenger.ui.firebase

import android.content.Context
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.remote.friends.FriendsRemoteImpl
import com.example.messenger.remote.service.IApiService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import javax.inject.Inject

class NotificationHelper @Inject constructor(val context: Context) {

    companion object {
        const val MESSAGE = "message"
        const val JSON_MESSAGE = "firebase_json_message"
        const val TYPE = "type"
        const val TYPE_ADD_FRIEND = "addFriend"
        const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"
    }

    fun sendNotification(remoteMessage: RemoteMessage) {
        if (remoteMessage?.data == null) {
            return
        }

        val message = remoteMessage.data[MESSAGE]
        val jsonMessage = JSONObject(message).getJSONObject(JSON_MESSAGE)

        val type = jsonMessage.getString(TYPE)
        when (type) {
            TYPE_ADD_FRIEND -> sendAddFriendNotification(jsonMessage)
            TYPE_APPROVED_FRIEND -> sendApproveFriendNotification(jsonMessage)
            TYPE_CANCELLED_FRIEND_REQUEST -> sendCancelFriendNotification(jsonMessage)
        }
    }

    private fun sendAddFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        showMessage("${friend.name} ${context.getString(R.string.wants_add_as_friend)} ")
    }

    private fun sendApproveFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        showMessage("${friend.name} ${context.getString(R.string.approved_friend_request)} ")
    }

    private fun sendCancelFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)
        showMessage("${friend.name} ${context.getString(R.string.cancelled_friend_request)} ")
    }

    private fun parseFriend(jsonMessage: JSONObject): FriendEntity {
        val requestUser = if (jsonMessage.has(IApiService.PARAM_REQUEST_USER)) {
            jsonMessage.getJSONObject(IApiService.PARAM_REQUEST_USER)
        } else {
            jsonMessage.getJSONObject(IApiService.PARAM_APPROVED_USER)
        }

        val friendsId = jsonMessage.getLong(IApiService.PARAM_FRIENDS_ID)

        val id = requestUser.getLong(IApiService.PARAM_USER_ID)
        val name = requestUser.getString(IApiService.PARAM_NAME)
        val email = requestUser.getString(IApiService.PARAM_EMAIL)
        val status = requestUser.getString(IApiService.PARAM_STATUS)
        val image = requestUser.getString(IApiService.PARAM_USER_ID)

        return FriendEntity(id, name, email, friendsId, status, image)
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}