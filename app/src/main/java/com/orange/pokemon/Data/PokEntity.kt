package com.orange.pokemon.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokEntity (
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val name: String,
    val imageurl: String,
    val xdescription: String

    )