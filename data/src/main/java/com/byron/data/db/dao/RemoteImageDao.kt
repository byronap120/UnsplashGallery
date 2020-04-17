package com.byron.data.db.dao

import androidx.room.*
import com.byron.data.model.RemoteImage

@Dao
interface RemoteImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveImage(image: RemoteImage)

    @Query("SELECT * FROM favorite_images")
    fun favoriteImages(): List<RemoteImage>

    @Delete
    fun deleteFavoriteImage(image: RemoteImage)
}

