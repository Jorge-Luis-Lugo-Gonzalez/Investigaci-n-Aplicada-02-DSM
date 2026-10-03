# Administración de productos – Ejemplo con Jetpack Compose

Aplicación de ejemplo de la **Investigación no. 2: Diseño de interfaces utilizando Jetpack Compose**
(DSM441 – Desarrollo de Software para Móviles, Universidad Don Bosco).

Muestra cómo construir una interfaz **declarativa y reutilizable** en Android usando únicamente funciones `@Composable`, sin archivos XML de diseño.

## Funcionalidades

- Registrar productos con descripción, precio, cantidad y categoría.
- **Agregar**, **actualizar** (tocando un producto de la lista) y **eliminar** (con diálogo de confirmación).
- Validación de campos con mensajes de error en el formulario.
- Búsqueda de productos por descripción.
- Lista ordenada alfabéticamente y mensajes con `Snackbar`.

> Los datos se guardan **en memoria** (dentro del `ViewModel`); al cerrar la app se pierden. El objetivo del ejemplo es el diseño de la interfaz.

## Requisitos

- Android Studio (versión reciente con soporte para Android Gradle Plugin 9.x).
- JDK 11 o superior (el que incluye Android Studio).
- Dispositivo o emulador con Android 7.0 (API 24) o superior.

## Cómo ejecutarlo

1. Clona el repositorio:
   ```bash
   git clone https://github.com/Jorge-Luis-Lugo-Gonzalez/Investigaci-n-Aplicada-02-DSM.git
   ```
2. Abre la carpeta del proyecto en Android Studio y espera a que termine la sincronización de Gradle (*Sync Now*).
3. Selecciona un emulador o dispositivo y presiona **Run ▶**.
