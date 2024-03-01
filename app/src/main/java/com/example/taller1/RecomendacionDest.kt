package com.example.taller1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject

class RecomendacionDest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recomendacion_dest)

        val categoriaMasFrecuente = encontrarCategoriaMasFrecuente()
        val destinoAzar = seleccionarDestinoAzar(categoriaMasFrecuente)

        findViewById<TextView>(R.id.nombreRec).text = destinoAzar?.getString("nombre")
        findViewById<TextView>(R.id.paisRec).text = destinoAzar?.getString("pais")
        findViewById<TextView>(R.id.categoDest).text = destinoAzar?.getString("categoria")
        findViewById<TextView>(R.id.planRec).text = destinoAzar?.getString("plan")
        findViewById<TextView>(R.id.precioDest).text = destinoAzar?.getString("precio")
    }

    private fun encontrarCategoriaMasFrecuente(): String {
        val listaFavoritos = MainActivity.ListaFavs

        val categoriasFrecuentes = mutableMapOf<String, Int>()

        for (destinoJson in listaFavoritos) {
            val categoria = destinoJson.getString("categoria")
            categoriasFrecuentes[categoria] = categoriasFrecuentes.getOrDefault(categoria, 0) + 1
        }

        var categoriaMasFrecuente = ""
        var frecuenciaMaxima = 0
        for ((categoria, frecuencia) in categoriasFrecuentes) {
            if (frecuencia > frecuenciaMaxima) {
                categoriaMasFrecuente = categoria
                frecuenciaMaxima = frecuencia
            }
        }

        return categoriaMasFrecuente
    }

    private fun seleccionarDestinoAzar(categoria: String): JSONObject? {
        val listaFavoritos = MainActivity.ListaFavs // Obtener la lista de favoritos

        // Filtrar los destinos que pertenecen a la categoría más frecuente
        val destinosCategoria = listaFavoritos.filter { it.getString("categoria") == categoria }

        // Seleccionar un destino al azar de entre los destinos de la categoría más frecuente
        return destinosCategoria.randomOrNull()
    }
}