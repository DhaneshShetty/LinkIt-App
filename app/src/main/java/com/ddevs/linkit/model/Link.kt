package com.ddevs.linkit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL
import java.sql.Time

@Entity(tableName = "links_table")
data class Link(
    @PrimaryKey(autoGenerate = true)
    val linkId:Long=0L,
    @ColumnInfo(name="url")
    val url:String="",
    @ColumnInfo(name="title")
    val title:String="",
    @ColumnInfo(name="category")
    val category:String="",
    @ColumnInfo(name="notes")
    val notes:String="")