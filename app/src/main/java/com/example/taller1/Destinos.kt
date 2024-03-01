package com.example.taller1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class Destinos : AppCompatActivity() {

    data class Destino(
        val nombre: String,
        val pais: String,
        val categoria: String,
        val plan: String,
        val precio: Int
    )

    val listaDestinos: MutableList<Destino> = ArrayList()
    val arreglo: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinos)

        val bundle = intent.extras
        val categoriaSeleccionada = bundle?.getString("categoria")

        val lista = findViewById<ListView>(R.id.lst1)

        val json = JSONObject(loadJSONFromAsset())
        val destinosJSON = json.getJSONArray("destinos")
        for(i in 0 until destinosJSON.length()){
            val jsonObject = destinosJSON.getJSONObject(i)
            val nombre = jsonObject.getString("nombre")
            val pais = jsonObject.getString("pais")
            val categoria = jsonObject.getString("categoria")
            val plan = jsonObject.getString("plan")
            val precio = jsonObject.getInt("precio")

            val destino = Destino(nombre, pais, categoria, plan, precio)
            listaDestinos.add(destino)
        }

        val destinosFiltrados = if (categoriaSeleccionada == "Todos") {
            listaDestinos
        } else {
            listaDestinos.filter { it.categoria == categoriaSeleccionada }
        }

        arreglo.clear()
        destinosFiltrados.forEach { destino ->
            arreglo.add(destino.nombre)
        }

        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, arreglo)
        lista.adapter = adaptador

        setupItemClickListener(lista, destinosFiltrados)
    }

    private fun setupItemClickListener(lista: ListView, destinosFiltrados: List<Destino>) {
        lista.setOnItemClickListener { parent, view, position, id ->
            val destinoSeleccionado = destinosFiltrados[position]

            val intent = Intent(this@Destinos, ExplorarDestino::class.java)

            val bundle = Bundle()
            bundle.putString("nombre", destinoSeleccionado.nombre)
            bundle.putString("pais", destinoSeleccionado.pais)
            bundle.putString("categoria", destinoSeleccionado.categoria)
            bundle.putString("plan", destinoSeleccionado.plan)
            bundle.putInt("precio", destinoSeleccionado.precio)

            intent.putExtras(bundle)

            startActivity(intent)
        }
    }


    fun loadJSONFromAsset(): String?{
        var json: String? = null
        try{
            val isS : InputStream = assets.open("destinos.json")
            val size = isS.available()
            val buffer = ByteArray(size)
            isS.read(buffer)
            isS.close()
            json = String(buffer, Charset.forName("UTF-8"))

        } catch(ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }
}