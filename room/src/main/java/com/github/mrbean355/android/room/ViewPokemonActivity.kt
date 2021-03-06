package com.github.mrbean355.android.room

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.mrbean355.android.framework.Pokemon
import com.github.mrbean355.android.framework.PokemonAdapter
import com.github.mrbean355.android.framework.PokemonRepository
import com.github.mrbean355.android.room.db.PokemonDatabase
import com.github.mrbean355.android.room.db.PokemonEntity
import kotlinx.android.synthetic.main.activity_view_pokemon.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * NOTE: All this logic should be moved out of the activity!
 * For example, a view model + repository could be used.
 */
class ViewPokemonActivity : AppCompatActivity() {
    private lateinit var adapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pokemon)

        adapter = PokemonAdapter()
        recycler_view.adapter = adapter
        loadPokemon()
    }

    private fun loadPokemon() {
        GlobalScope.launch(context = IO) {
            var items = loadFromDatabase()
            if (items.isEmpty()) {
                items = loadFromService()
                saveToDatabase(items)
            }
            withContext(Main) {
                adapter.setItems(items)
                progress_bar.visibility = View.GONE
            }
        }
    }

    private fun loadFromDatabase(): List<Pokemon> {
        val db = PokemonDatabase.getInstance(application)
        val dao = db.pokemonDao()
        return dao.getAll().map { Pokemon(it.name, it.url) }
    }

    private fun saveToDatabase(items: List<Pokemon>) {
        val db = PokemonDatabase.getInstance(application)
        val dao = db.pokemonDao()
        dao.insertAll(items.map { PokemonEntity(0, it.name, it.url) })
    }

    private suspend fun loadFromService(): List<Pokemon> {
        return PokemonRepository().getAll()
    }
}
