package com.ddevs.linkit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ddevs.linkit.model.Link
import com.ddevs.linkit.model.MeetLink
import com.ddevs.linkit.model.ShareLink

@Database(entities=[Link::class,ShareLink::class,MeetLink::class],version=1,exportSchema = false)
abstract class LinkDatabase: RoomDatabase() {
    abstract val linkDao:LinkDAO

    companion object{
        @Volatile
        private var INSTANCE:LinkDatabase?=null
        fun getInstance(context: Context):LinkDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        LinkDatabase::class.java,
                        "link_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE =instance
                }
                return instance
            }
        }
    }
}