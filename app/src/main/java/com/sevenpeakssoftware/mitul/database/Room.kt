package com.sevenpeakssoftware.mitul.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContentDao {
    @Query("select * from databasecontent")
    fun getContents(): LiveData<List<DatabaseContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseContent)
}

@Database(entities = [DatabaseContent::class], version = 1)
abstract class ContentDatabase : RoomDatabase() {
    abstract val contentDao: ContentDao
}

private lateinit var INSTANCE: ContentDatabase

fun getDatabase(context: Context): ContentDatabase {
    synchronized(ContentDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ContentDatabase::class.java,
                "contents").build()
        }
    }
    return INSTANCE
}