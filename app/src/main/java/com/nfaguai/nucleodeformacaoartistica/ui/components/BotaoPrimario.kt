package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun BotaoPrimario(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    carregando: Boolean = false,
) {
    Button(
        onClick = onClick,
        enabled = enabled && !carregando,
        modifier = modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = NucleoColors.Laranja,
            contentColor = Color.White,
        ),
    ) {
        if (carregando) {
            CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
        } else {
            Text(texto, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview
@Composable
private fun BotaoPrimarioPreview() {
    BotaoPrimario(texto = "Teste", onClick = {})
}