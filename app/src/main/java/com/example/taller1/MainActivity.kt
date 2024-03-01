package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoriasElegibles = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
        val categoriaSeleccionada = findViewById<Spinner>(R.id.spinner1)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriasElegibles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSeleccionada.adapter = adapter

        val btnExp = findViewById<Button>(R.id.btn1)

        btnExp.setOnClickListener{
            val catSec = categoriaSeleccionada.selectedItem.toString()
            val cat = Bundle().apply {
                putString("categoria", catSec)
            }
            val intent = Intent(this, Destinos::class.java).apply {
                putExtras(cat)
            }
            startActivity(intent)
        }

        val btnFav = findViewById<Button>(R.id.btn2)
        btnFav.setOnClickListener{
            val intent = Intent(this, FavoriteDest::class.java)
            startActivity(intent)
        }

        val btnRecF = findViewById<Button>(R.id.btn3)
        btnRecF.setOnClickListener{
            val intent = Intent(this, RecomendacionDest::class.java)
            startActivity(intent)
        }
    }

    companion object {
        var ListaFavs = mutableListOf<JSONObject>()

        fun addToFavoritos(destino: JSONObject) {
            ListaFavs.add(destino)
        }
    }

}