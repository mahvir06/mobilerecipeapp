package com.example.cw2
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey val name: String,
    val drink: String?,
    val category: String?,
    val area: String?,
    val instructions: String?,
    val youtube: String?,
    val mealthumb: String?,
    val tags: String?,
    val ingredients: String?,
    val measures: String?,
    val Source: String?,
    val ImageSource: String?,
    val CreativeCommonsConfirmed: String?,
    val dateModified: String?
)