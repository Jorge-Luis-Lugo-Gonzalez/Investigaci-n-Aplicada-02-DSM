package com.example.investigaciondsm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.investigaciondsm.ui.theme.ProductosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductosAppTheme {          // tema generado por la plantilla
                ProductosScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    ProductosAppTheme {
        FormularioProducto(
            nombre = "Cereal", precio = "1.50", cantidad = "25",
            categoria = "Abarrotes", categorias = listOf("Abarrotes", "Carnes"),
            editando = false, mostrarErrores = false,
            onNombre = {}, onPrecio = {}, onCantidad = {}, onCategoria = {},
            onGuardar = {}, onCancelar = {}
        )
    }
}
