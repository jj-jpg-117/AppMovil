package com.example.myapplication.model

/**
 * Un renglón del pedido: un producto y cuántas unidades se pidieron.
 */
data class ItemPedido(
    val producto: Producto,
    val cantidad: Int
) {
    init {
        require(cantidad > 0) { "La cantidad debe ser positiva, era $cantidad" }
    }

    /** Precio unitario × cantidad. */
    val total: Int
        get() = producto.precio * cantidad
}

/**
 * Lógica de negocio del pedido.
 *
 * Extraída de la UI para que sea testeable con pruebas
 * unitarias simples (ver ResumenPedidoTest).
 */
data class ResumenPedido(
    val items: List<ItemPedido>,
    val precioEnvio: Int = Catalogo.PRECIO_ENVIO
) {
    /** Suma de (precio × cantidad) de todos los ítems. */
    val subtotal: Int
        get() = items.sumOf { it.total }

    /** Subtotal más el costo de envío. */
    val total: Int
        get() = subtotal + precioEnvio

    val estaVacio: Boolean
        get() = items.isEmpty()
}
