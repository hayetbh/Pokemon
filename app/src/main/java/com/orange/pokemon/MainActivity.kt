package com.orange.pokemon

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.orange.pokemon.Data.PokDao
import com.orange.pokemon.Data.PokDatabase
import com.orange.pokemon.Data.PokEntity
import com.orange.pokemon.adapter.PokAdapter
import com.orange.pokemon.databinding.ActivityMainBinding
import com.orange.pokemon.model.Pokemon
import com.orange.pokemon.networking.ApiService
import com.orange.pokemon.networking.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: PokDatabase
    private lateinit var dao: PokDao
    private lateinit var pokAdapter: PokAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokAdapter = PokAdapter()
        val service = NetworkClient().getRetrofit().create(ApiService::class.java)
        service.getAllPokemons().enqueue(object : Callback<List<Pokemon>> {

            override fun onResponse(call: Call<List<Pokemon>>, response: Response<List<Pokemon>>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "onResponse: ${response.body()?.get(0)}")

                    val listPokemon: List<Pokemon>? = response.body()
                    database = PokDatabase.getInstance(this@MainActivity)
                    dao = database.getPokemonDao()
                    GlobalScope.launch(Dispatchers.Main) {
                        dao.insertAll(
                            listPokemon!!.map {
                                PokEntity(
                                    name = it.name,
                                    xdescription = it.xdescription,
                                    imageurl = it.imageurl
                                )
                            }
                        )

                        val list = dao.getAll()
                        pokAdapter.submitList(list)
                        binding.recyclerviewList.apply {
                            layoutManager = GridLayoutManager(this@MainActivity, 3)
                            adapter = pokAdapter
                        }

                    }


                }
            }

            override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()

                database = PokDatabase.getInstance(this@MainActivity)
                dao = database.getPokemonDao()
                GlobalScope.launch(Dispatchers.Main) {
                    val list = dao.getAll()
                    pokAdapter.submitList(list)
                    binding.recyclerviewList.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = pokAdapter
                    }
                }
            }


        })


    }


}