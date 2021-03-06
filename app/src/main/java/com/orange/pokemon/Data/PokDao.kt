package com.orange.pokemon.Data

import androidx.room.*

@Dao
interface PokDao {
    @Insert
    suspend fun insertOne(entity: PokEntity)

    @Insert
    suspend fun insertAll(entities: List<PokEntity>)

    @Delete
    suspend fun deleteOne(entity: PokEntity)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAll(): List<PokEntity>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    suspend fun getOneByID(id: Int): PokEntity

    @Update
    suspend fun updateOne(pokemonEntity: PokEntity)

}