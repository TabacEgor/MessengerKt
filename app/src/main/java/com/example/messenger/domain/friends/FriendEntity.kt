package com.example.messenger.domain.friends

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "friends_table")
class FriendEntity(
    @PrimaryKey
    @SerializedName("user_id")
    var id: Long,
    var name: String,
    var email: String,
    @ColumnInfo(name = "friends_id")
    @SerializedName("friends_id")
    var friendsId: Long,
    var status: String,
    var image: String,
    @ColumnInfo(name = "is_request")
    var isRequest: Int = 0, // 0 -> friend 1->friend request
    @ColumnInfo(name = "last_seen")
    @SerializedName("last_seen")
    var lastSeen: Long = 0
)