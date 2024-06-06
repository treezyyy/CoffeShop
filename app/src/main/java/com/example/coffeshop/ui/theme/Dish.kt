package com.example.coffeshop.ui.theme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dishes")
data class Dish(
    @PrimaryKey(autoGenerate = true) val iddish: Int = 0,
    val name: String,
    val price: Double,
    val type: String
)