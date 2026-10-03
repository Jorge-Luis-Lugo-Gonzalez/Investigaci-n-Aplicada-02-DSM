package com.example.investigaciondsm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FormularioProducto(
    nombre: String, precio: String, cantidad: String, categoria: String,
    categorias: List<String>, editando: Boolean, mostrarErrores: Boolean,
    onNombre: (String) -> Unit, onPrecio: (String) -> Unit,
    onCantidad: (String) -> Unit, onCategoria: (String) -> Unit,
    onGuardar: () -> Unit, onCancelar: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = nombre, onValueChange = onNombre,
            label = { Text("Producto") }, singleLine = true,
            isError = mostrarErrores && nombre.isBlank(),
            supportingText = {
                if (mostrarErrores && nombre.isBlank()) Text("Ingrese el nombre del producto")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = precio, onValueChange = onPrecio,
            label = { Text("Precio") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = mostrarErrores && precio.toDoubleOrNull() == null,
            supportingText = {
                if (mostrarErrores && precio.toDoubleOrNull() == null)
                    Text("Ingrese el precio del producto")
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cantidad, onValueChange = onCantidad,
            label = { Text("Cantidad inicial") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = mostrarErrores && cantidad.toIntOrNull() == null,
            supportingText = {
                if (mostrarErrores && cantidad.toIntOrNull() == null)
                    Text("Ingrese la cantidad inicial")
            },
            modifier = Modifier.fillMaxWidth()
        )
        SelectorCategoria(categoria, categorias, onCategoria)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onGuardar, modifier = Modifier.weight(1f)) {
                Text(if (editando) "Actualizar" else "Agregar")
            }
            if (editando) {
                OutlinedButton(onClick = onCancelar, modifier = Modifier.weight(1f)) {
                    Text("Cancelar")
                }
            }
        }
    }
}

@Composable
fun SelectorCategoria(
    seleccion: String, opciones: List<String>, onSeleccion: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(
            onClick = { expandido = true }, modifier = Modifier.fillMaxWidth()
        ) { Text("Categoría: $seleccion") }
        DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = { onSeleccion(opcion); expandido = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoItem(
    producto: Producto, seleccionado: Boolean,
    onClick: () -> Unit, onEliminar: () -> Unit
) {
    val precioTexto = "$%.2f".format(producto.precio)
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionado) MaterialTheme.colorScheme.primaryContainer
                             else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(producto.descripcion, style = MaterialTheme.typography.titleMedium)
                Text("${producto.categoria} | $precioTexto | Cantidad: ${producto.cantidad}")
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
