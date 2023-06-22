package com.myschoolproject.babble.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myschoolproject.babble.data.source.local.entity.LikeListEntity

@Database(
    entities = [LikeListEntity::class],
    version = 2
)
abstract class LikeListDatabase: RoomDatabase() {

    abstract fun LikeListDao(): LikeListDao
}