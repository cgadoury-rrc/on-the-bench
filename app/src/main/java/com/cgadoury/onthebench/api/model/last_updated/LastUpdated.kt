package com.cgadoury.onthebench.api.model.last_updated

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "last_updated")
data class LastUpdated(
    @PrimaryKey
    val id: Int = 1,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = 0
)

