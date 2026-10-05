package com.example.myapplication.model

import androidx.annotation.StringRes
import com.example.myapplication.R

/**
 * Producto del menú.
 *
 * - [id]: clave estable usada en LazyColumn y en la base de datos.
 * - Los textos son referencias a recursos (@StringRes) para respetar
 *   la localización de Android.
 * - El precio siempre es un entero en pesos (nunca texto).
 */
data class Producto(
    val id: String,
    @param:StringRes val nombreRes: Int,
    val emoji: String,
    val precio: Int,
    @param:StringRes val descripcionRes: Int? = null
)

/**
 * Catálogo: única fuente de verdad para productos y precios.
 * Agregar un producto al menú es agregarlo aquí y nada más.
 */
object Catalogo {

    const val PRECIO_ENVIO = 5_000

    val hamburguesa = Producto("hamburguesa", R.string.producto_hamburguesa, "🍔", 20_000, R.string.producto_hamburguesa_desc)
    val pizza = Producto("pizza", R.string.producto_pizza, "🍕", 12_000, R.string.producto_pizza_desc)
    val tacos = Producto("tacos", R.string.producto_tacos, "🌮", 15_000, R.string.producto_tacos_desc)
    val burrito = Producto("burrito", R.string.producto_burrito, "🌯", 14_000, R.string.producto_burrito_desc)
    val pollo = Producto("pollo", R.string.producto_pollo, "🍗", 18_000, R.string.producto_pollo_desc)
    val hotdog = Producto("hotdog", R.string.producto_hotdog, "🌭", 8_000, R.string.producto_hotdog_desc)
    val ensalada = Producto("ensalada", R.string.producto_ensalada, "🥗", 10_000, R.string.producto_ensalada_desc)
    val papas = Producto("papas", R.string.producto_papas, "🍟", 5_000)
    val bebida = Producto("bebida", R.string.producto_bebida, "🥤", 4_000)
    val helado = Producto("helado", R.string.producto_helado, "🍦", 6_000, R.string.producto_helado_desc)

    /** Menú completo mostrado en la conversación (LazyColumn). */
    val menu: List<Producto> = listOf(
        hamburguesa, pizza, tacos, burrito, pollo,
        hotdog, ensalada, papas, bebida, helado
    )

    fun porId(id: String): Producto? = menu.find { it.id == id }
}
