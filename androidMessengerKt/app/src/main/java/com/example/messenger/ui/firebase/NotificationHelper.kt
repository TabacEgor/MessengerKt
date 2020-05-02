package com.example.messenger.ui.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.messenger.R
import com.example.messenger.domain.friends.FriendEntity
import com.example.messenger.domain.messages.ContactEntity
import com.example.messenger.domain.messages.GetMessagesWithContact
import com.example.messenger.domain.messages.MessageEntity
import com.example.messenger.remote.friends.FriendsRemoteImpl
import com.example.messenger.remote.service.IApiService
import com.example.messenger.ui.home.HomeActivity
import com.example.messenger.ui.home.MessagesActivity
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    val context: Context,
    val getMessagesWithContact: GetMessagesWithContact
): ContextWrapper(context) {

    companion object {
        const val MESSAGE = "message"
        const val JSON_MESSAGE = "firebase_json_message"
        const val TYPE = "type"
        const val TYPE_ADD_FRIEND = "addFriend"
        const val TYPE_APPROVED_FRIEND = "approveFriendRequest"
        const val TYPE_CANCELLED_FRIEND_REQUEST = "cancelFriendRequest"
        const val TYPE_SEND_MESSAGE = "sendMessage"

        const val notificationId = 110
    }

    var mManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createChannels()
    }

    private fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create android channel
            val androidChannel = NotificationChannel(
                context.packageName,
                "${context.packageName}.notification_chanel", NotificationManager.IMPORTANCE_DEFAULT
            )
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true)
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true)
            // Sets the notification light color for notifications posted to this channel
            androidChannel.lightColor = Color.GREEN
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

            mManager.createNotificationChannel(androidChannel)
        }
    }

    fun sendNotification(remoteMessage: RemoteMessage?) {
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
            TYPE_SEND_MESSAGE -> sendMessageNotification(jsonMessage)
        }
    }

    private fun sendAddFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_ADD_FRIEND)

        createNotification(
            getString(R.string.friend_request),
            "${friend.name} ${context.getString(R.string.wants_add_as_friend)}",
            intent
        )
    }

    private fun sendApproveFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_APPROVED_FRIEND)

        createNotification(
            getString(R.string.friend_request_approved),
            "${friend.name} ${context.getString(R.string.approved_friend_request)}",
            intent
        )
    }

    private fun sendCancelFriendNotification(jsonMessage: JSONObject) {
        val friend = parseFriend(jsonMessage)

        val intent = Intent(context, HomeActivity::class.java)
        intent.putExtra("type", TYPE_CANCELLED_FRIEND_REQUEST)

        createNotification(
            getString(R.string.friend_request_cancelled),
            "${friend.name} ${context.getString(R.string.cancelled_friend_request)}",
            intent
        )
    }

    private fun sendMessageNotification(jsonMessage: JSONObject) {
        val message = parseMessage(jsonMessage)

        getMessagesWithContact(GetMessagesWithContact.Params(message.senderId, true))

        val intent = Intent(context, MessagesActivity::class.java)

        intent.putExtra(IApiService.PARAM_CONTACT_ID, message.contact?.id)
        intent.putExtra(IApiService.PARAM_NAME, message.contact?.name)
        intent.putExtra("type", TYPE_SEND_MESSAGE)

        createNotification(
            "${message.contact?.name} ${context.getString(R.string.send_message)}",
            message.message,
            intent
        )
    }

    private fun parseMessage(jsonMessage: JSONObject): MessageEntity {
        val senderUser = jsonMessage.getJSONObject(IApiService.PARAM_SENDER_USER)
        val senderName = senderUser.getString(IApiService.PARAM_NAME)
        val senderImage = senderUser.getString(IApiService.PARAM_IMAGE)

        val id = jsonMessage.getLong(IApiService.PARAM_MESSAGE_ID)
        val senderId = jsonMessage.getLong(IApiService.PARAM_SENDER_USER_ID)
        val receiverId = jsonMessage.getLong(IApiService.PARAM_RECEIVED_USER_ID)
        val message = jsonMessage.getString(IApiService.PARAM_MESSAGE)
        val type = jsonMessage.getInt(IApiService.PARAM_MESSAGE_TYPE)

        return MessageEntity(id, senderId, receiverId, message, 0, type, ContactEntity(senderId, senderName, senderImage))
    }

    private fun createNotification(title: String, message: String, intent: Intent) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        intent.action = "notification $notificationId"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val contentIntent = PendingIntent.getActivity(
            context, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val mBuilder = NotificationCompat.Builder(
            context, context.applicationContext.packageName
        )
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setSound(defaultSoundUri)
            .setAutoCancel(true)
            .setContentText(message)
        mBuilder.setContentIntent(contentIntent)
        mManager.notify(notificationId, mBuilder.build())
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