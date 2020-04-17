package com.byron.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.byron.data.db.dao.RemoteImageDao
import com.byron.data.model.RemoteImage

@Database(
    entities = [
        RemoteImage::class
    ],
    version = 4,
    exportSchema = false
)
abstract class UnsplashDataBase : RoomDatabase() {

    abstract fun remoteImageDao(): RemoteImageDao

}
