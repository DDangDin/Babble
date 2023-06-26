package com.myschoolproject.babble.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myschoolproject.babble.data.source.local.entity.ChatEntity
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity

@Database(
    entities = [ChatEntity::class],
    version = 1
)
abstract class ChatDatabase: RoomDatabase() {

    abstract fun ChatDao(): ChatDao
}