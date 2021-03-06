package com.orange.pokemon.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PokEntity::class],
    version = 1
)
abstract class PokDatabase : RoomDatabase() {

    abstract fun getPokemonDao(): PokDao

    companion object {

        private var instance: PokDatabase? = null

        fun getInstance(context: Context): PokDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    PokDatabase::class.java,
                    "pokemon_db"
                ).build()
            }
            return instance!!
        }
    }
}