package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun BotaoVoltar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val forma = RoundedCornerShape(8.dp)

    Box(
        modifier = modifier
            .size(32.dp)
            .clip(forma)
            .background(NucleoColors.Fundo)
            .border(1.dp, NucleoColors.Borda, forma)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Voltar",
            tint = NucleoColors.Texto,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Preview
@Composable
private fun BotaoVoltarPreview() {
    BotaoVoltar(onClick = {})
}