package com.example.myapplication

import com.example.myapplication.model.Catalogo
import com.example.myapplication.model.ItemPedido
import com.example.myapplication.model.ResumenPedido
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Pruebas de la lógica de negocio del pedido.
 * Al extraerla de la UI se puede verificar sin emulador.
 */
class ResumenPedidoTest {

    @Test
    fun `subtotal suma precio por cantidad de cada item`() {
        val resumen = ResumenPedido(
            items = listOf(
                ItemPedido(Catalogo.hamburguesa, cantidad = 2), // 40.000
                ItemPedido(Catalogo.papas, cantidad = 1)        //  5.000
            )
        )

        assertEquals(45_000, resumen.subtotal)
    }

    @Test
    fun `total agrega el costo de envio al subtotal`() {
        val resumen = ResumenPedido(
            items = listOf(ItemPedido(Catalogo.hamburguesa, cantidad = 1))
        )

        assertEquals(20_000 + Catalogo.PRECIO_ENVIO, resumen.total)
    }

    @Test
    fun `total respeta un envio personalizado`() {
        val resumen = ResumenPedido(
            items = listOf(ItemPedido(Catalogo.hamburguesa, cantidad = 1)),
            precioEnvio = 3_000
        )

        assertEquals(23_000, resumen.total)
    }

    @Test
    fun `un pedido sin items esta vacio y su subtotal es cero`() {
        val resumen = ResumenPedido(items = emptyList())

        assertEquals(true, resumen.estaVacio)
        assertEquals(0, resumen.subtotal)
    }
}
