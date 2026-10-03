package com.example.investigaciondsm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(vm: ProductosViewModel = viewModel()) {
    // Estado del formulario (sobrevive a la rotación)
    var seleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var cantidad by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf(vm.categorias.first()) }
    var consulta by rememberSaveable { mutableStateOf("") }
    var mostrarErrores by remember { mutableStateOf(false) }
    var aEliminar by remember { mutableStateOf<Producto?>(null) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun mensaje(texto: String) { scope.launch { snackbar.showSnackbar(texto) } }
    fun limpiar() {
        seleccionado = null; nombre = ""; precio = ""; cantidad = ""
        mostrarErrores = false
    }
    val lista = remember(vm.productos, consulta) {
        vm.productos
            .filter { it.descripcion.contains(consulta, ignoreCase = true) }   // Buscar
            .sortedBy { it.descripcion.lowercase() }                           // ASC
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Administración de productos") }) },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FormularioProducto(
                    nombre, precio, cantidad, categoria, vm.categorias,
                    editando = seleccionado != null,
                    mostrarErrores = mostrarErrores,
                    onNombre = { nombre = it },
                    onPrecio = { precio = it },
                    onCantidad = { cantidad = it },
                    onCategoria = { categoria = it },
                    onGuardar = {
                        mostrarErrores = true
                        val p = precio.toDoubleOrNull()
                        val c = cantidad.toIntOrNull()
                        if (nombre.isNotBlank() && p != null && c != null) {
                            val id = seleccionado
                            if (id == null) {
                                vm.agregar(nombre.trim(), p, c, categoria)
                                mensaje("Producto agregado")
                            } else {
                                vm.actualizar(id, nombre.trim(), p, c, categoria)
                                mensaje("Producto actualizado")
                            }
                            limpiar()
                        } else {
                            mensaje("Se han generado algunos errores, favor verifiquelos")
                        }
                    },
                    onCancelar = { limpiar() }
                )
            }
            item {
                OutlinedTextField(
                    value = consulta, onValueChange = { consulta = it },
                    label = { Text("Buscar producto") }, singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            items(lista, key = { it.id }) { p ->
                ProductoItem(
                    producto = p,
                    seleccionado = p.id == seleccionado,
                    onClick = {                       // cargar datos para editar
                        seleccionado = p.id
                        nombre = p.descripcion
                        precio = p.precio.toString()
                        cantidad = p.cantidad.toString()
                        categoria = p.categoria
                    },
                    onEliminar = { aEliminar = p }
                )
            }
        }
    }
    // Confirmación de borrado (AlertDialog)
    aEliminar?.let { p ->
        AlertDialog(
            onDismissRequest = { aEliminar = null },
            title = { Text("Confirmación") },
            text = { Text("¿Está seguro de eliminar \"${p.descripcion}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    vm.eliminar(p.id)
                    if (seleccionado == p.id) limpiar()
                    aEliminar = null
                    mensaje("Producto eliminado")
                }) { Text("Sí") }
            },
            dismissButton = { TextButton(onClick = { aEliminar = null }) { Text("No") } }
        )
    }
}
