package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.Catalogo
import com.example.myapplication.model.ItemPedido
import com.example.myapplication.model.ModoTema
import com.example.myapplication.model.Producto
import com.example.myapplication.ui.components.BotonModoTema
import com.example.myapplication.ui.components.BotonPrimario
import com.example.myapplication.ui.components.ControlCantidad
import com.example.myapplication.ui.components.OrderBotCard
import com.example.myapplication.ui.components.TarjetaProducto
import com.example.myapplication.ui.theme.OrderBotTheme
import com.example.myapplication.util.aPrecio

/**
 * Pantalla 2: el bot saluda y muestra el catálogo completo.
 *
 * El usuario arma su carrito con los controles de cantidad de cada
 * producto y continúa cuando hay al menos uno.
 *
 * La pantalla es "tonta" (stateless): recibe el carrito y notifica
 * las acciones; el estado vive en el PedidoViewModel.
 *
 * @param items productos ya elegidos con su cantidad.
 * @param onAgregar suma una unidad del producto.
 * @param onQuitar resta una unidad del producto.
 * @param onContinuar navega a la recomendación de complementos.
 */
@Composable
fun PantallaConversacion(
    items: List<ItemPedido>,
    onAgregar: (Producto) -> Unit,
    onQuitar: (Producto) -> Unit,
    onContinuar: () -> Unit,
    modoTema: ModoTema,
    onCambiarTema: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(20.dp)
    ) {
        // Encabezado con el selector de tema a la derecha.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.conversacion_titulo),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            BotonModoTema(modo = modoTema, onClick = onCambiarTema)
        }

        Text(
            text = stringResource(R.string.conversacion_subtitulo),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje del bot
        OrderBotCard {
            Text(
                text = stringResource(R.string.conversacion_saludo),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.conversacion_pregunta),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Catálogo con scroll: solo compone las filas visibles,
        // por eso se usa LazyColumn y no una Column con forEach.
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(
                items = Catalogo.menu,
                // La key estable evita recomposiciones al reordenar.
                key = { it.id }
            ) { producto ->
                val cantidad = items
                    .firstOrNull { it.producto.id == producto.id }
                    ?.cantidad ?: 0

                TarjetaProducto(
                    emoji = producto.emoji,
                    nombre = stringResource(producto.nombreRes),
                    descripcion = producto.descripcionRes?.let { stringResource(it) },
                    precio = producto.precio.aPrecio(),
                    accion = {
                        if (cantidad == 0) {
                            Button(
                                onClick = { onAgregar(producto) },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = stringResource(R.string.producto_boton_elegir))
                            }
                        } else {
                            ControlCantidad(
                                cantidad = cantidad,
                                onAgregar = { onAgregar(producto) },
                                onQuitar = { onQuitar(producto) }
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        BotonPrimario(
            texto = stringResource(R.string.conversacion_boton_continuar),
            onClick = onContinuar,
            enabled = items.isNotEmpty()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaConversacionPreview() {
    OrderBotTheme {
        PantallaConversacion(
            items = listOf(ItemPedido(Catalogo.hamburguesa, cantidad = 2)),
            onAgregar = {},
            onQuitar = {},
            onContinuar = {},
            modoTema = ModoTema.SISTEMA,
            onCambiarTema = {}
        )
    }
}
