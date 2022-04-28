package com.ddevs.linkit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "share_link_table")
data class ShareLink(@PrimaryKey(autoGenerate = true)
                     val linkId:Long=0L,
                     @ColumnInfo(name="url")
                     val url:String="")
