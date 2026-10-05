package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Altura y esquinas unificadas para todos los botones de la app.
private val AlturaBoton = 52.dp
private val RadioBoton = 14.dp

/**
 * Botón principal de la app. Centraliza el estilo para no
 * repetir forma, altura y colores en cada pantalla.
 */
@Composable
fun BotonPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(AlturaBoton),
        enabled = enabled,
        shape = RoundedCornerShape(RadioBoton)
    ) {
        Text(text = texto, fontWeight = FontWeight.Bold)
    }
}

/** Variante secundaria (con borde) de [BotonPrimario]. */
@Composable
fun BotonSecundario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(AlturaBoton),
        shape = RoundedCornerShape(RadioBoton)
    ) {
        Text(text = texto)
    }
}
