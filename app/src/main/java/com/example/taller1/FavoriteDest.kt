package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.taller1.MainActivity.Companion.ListaFavs

class FavoriteDest : AppCompatActivity() {
    val nombresFav: MutableSet<String> = HashSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_dest)

        val listFav = findViewById<ListView>(R.id.listFav)
        for (destinoJson in ListaFavs) {
            val nombre = destinoJson.getString("nombre")
            nombresFav.add(nombre)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresFav.toList()) // Convertir el conjunto a una lista antes de pasarlo al adapter

        listFav.adapter = adapter
    }
}
