package com.ddevs.linkit.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.sql.*

@Entity(tableName = "meet_link_table")
data class MeetLink(
        @PrimaryKey(autoGenerate = true)
        val id:Long=0L,
        @ColumnInfo(name="url")
        val meetUrl:String="",
        @ColumnInfo(name="title")
        val title:String="",
        @ColumnInfo(name="category")
        val category:String="",
        @ColumnInfo(name="notes")
        val notes:String="",
        @ColumnInfo(name="start_time")
        val startTime: String= "",
        @ColumnInfo(name="date")
        val date: Long=0
)
