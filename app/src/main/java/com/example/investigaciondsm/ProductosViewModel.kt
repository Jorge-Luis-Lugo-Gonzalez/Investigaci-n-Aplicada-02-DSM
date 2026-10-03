package com.example.investigaciondsm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ProductosViewModel : ViewModel() {
    val categorias = listOf(
        "Abarrotes", "Carnes", "Embutidos", "Mariscos", "Pescado",
        "Bebidas", "Verduras", "Frutas", "Bebidas Carbonatadas",
        "Bebidas no carbonatadas"
    )
    private var siguienteId = 1

    // Estado observable: al cambiar, la interfaz se recompone
    var productos by mutableStateOf(emptyList<Producto>())
        private set

    fun agregar(desc: String, precio: Double, cant: Int, cat: String) {
        productos = productos + Producto(siguienteId++, desc, precio, cant, cat)
    }

    fun actualizar(id: Int, desc: String, precio: Double, cant: Int, cat: String) {
        productos = productos.map {
            if (it.id == id) it.copy(
                descripcion = desc, precio = precio, cantidad = cant, categoria = cat
            ) else it
        }
    }

    fun eliminar(id: Int) {
        productos = productos.filterNot { it.id == id }
    }
}
