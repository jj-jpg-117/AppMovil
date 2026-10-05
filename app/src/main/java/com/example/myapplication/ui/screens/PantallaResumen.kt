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
import androidx.compose.material3.HorizontalDivider
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
import com.example.myapplication.model.MetodoPago
import com.example.myapplication.model.ModoTema
import com.example.myapplication.model.ResumenPedido
import com.example.myapplication.ui.components.BotonModoTema
import com.example.myapplication.ui.components.BotonPrimario
import com.example.myapplication.ui.components.FilaPrecio
import com.example.myapplication.ui.components.OrderBotCard
import com.example.myapplication.ui.theme.OrderBotTheme
import com.example.myapplication.util.aPrecio

/**
 * Pantalla 4: detalle del pedido antes de confirmar.
 *
 * Recibe un [ResumenPedido] ya calculado: la pantalla solo
 * muestra datos, no contiene lógica de negocio.
 *
 * @param direccion dirección escrita en la pantalla de entrega.
 * @param metodoPago método de pago elegido en la pantalla de entrega.
 * @param onConfirmar navega a la pantalla de confirmación.
 * @param modoTema modo de tema activo (para el botón de cambio).
 * @param onCambiarTema alterna claro → oscuro → automático.
 */
@Composable
fun PantallaResumen(
    resumen: ResumenPedido,
    direccion: String,
    metodoPago: MetodoPago,
    onConfirmar: () -> Unit,
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
        // Título con el selector de tema a la derecha.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.resumen_titulo),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            BotonModoTema(modo = modoTema, onClick = onCambiarTema)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Productos y totales
        OrderBotCard {
            Text(
                text = stringResource(R.string.resumen_seccion_productos),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Una fila por ítem: "🍔 Hamburguesa ×2" con su total por línea.
            resumen.items.forEach { item ->
                val cantidad = if (item.cantidad > 1) " ×${item.cantidad}" else ""
                FilaPrecio(
                    nombre = "${item.producto.emoji} ${stringResource(item.producto.nombreRes)}$cantidad",
                    precio = item.total.aPrecio()
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            FilaPrecio(
                nombre = stringResource(R.string.resumen_subtotal),
                precio = resumen.subtotal.aPrecio()
            )
            FilaPrecio(
                nombre = stringResource(R.string.resumen_envio),
                precio = resumen.precioEnvio.aPrecio()
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            FilaPrecio(
                nombre = stringResource(R.string.resumen_total),
                precio = resumen.total.aPrecio(),
                destacado = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Datos de entrega
        OrderBotCard {
            Text(
                text = stringResource(R.string.resumen_seccion_entrega),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.resumen_direccion, direccion),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(
                    R.string.resumen_pago,
                    stringResource(metodoPago.etiquetaRes)
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        BotonPrimario(
            texto = stringResource(R.string.resumen_boton_confirmar),
            onClick = onConfirmar
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PantallaResumenPreview() {
    OrderBotTheme {
        PantallaResumen(
            resumen = ResumenPedido(
                items = listOf(
                    ItemPedido(Catalogo.hamburguesa, cantidad = 1),
                    ItemPedido(Catalogo.papas, cantidad = 1),
                    ItemPedido(Catalogo.bebida, cantidad = 1)
                )
            ),
            direccion = "Calle 123 #45-67",
            metodoPago = MetodoPago.EFECTIVO,
            onConfirmar = {},
            modoTema = ModoTema.SISTEMA,
            onCambiarTema = {}
        )
    }
}
