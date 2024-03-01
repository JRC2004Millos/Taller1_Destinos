package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONObject

class ExplorarDestino : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorar_destino)

        val bundle = intent.extras

        val nombre = bundle?.getString("nombre")
        val pais = bundle?.getString("pais")
        val categoria = bundle?.getString("categoria")
        val plan = bundle?.getString("plan")
        val precio = bundle?.getInt("precio")
        val precioUSD = "$precio USD"

        findViewById<TextView>(R.id.nombreDest).text = nombre
        findViewById<TextView>(R.id.paisDest).text = pais
        findViewById<TextView>(R.id.categoDest).text = categoria
        findViewById<TextView>(R.id.planDest).text = plan
        findViewById<TextView>(R.id.precioDest).text = precioUSD

        val btnAddToFavorites = findViewById<Button>(R.id.btnFav)

        btnAddToFavorites.setOnClickListener {
            val destinoJson = JSONObject().apply {
                put("nombre", nombre)
                put("pais", pais)
                put("categoria", categoria)
                put("plan", plan)
                put("precio", precio)
            }

            MainActivity.addToFavoritos(destinoJson) // Agregar el destino a la lista de favoritos

            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()

            btnAddToFavorites.isEnabled = false
        }
    }
}