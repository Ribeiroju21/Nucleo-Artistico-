package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SwitchPresenca(
    presente: Boolean,
    onAlternar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val largura = 40.dp
    val tamanhoBolinha = 20.dp
    val margem = 2.dp

    val deslocamento by animateDpAsState(
        targetValue = if (presente) {
            largura - tamanhoBolinha - margem * 2
        } else {
            0.dp
        },
        label = "thumb",
    )

    val corFundo by animateColorAsState(
        if (presente) {
            NucleoColors.Verde
        } else {
            NucleoColors.Vermelho
        }, label = "track"
    )

    Box(
        modifier = modifier
            .size(width = largura, height = tamanhoBolinha + margem * 2)
            .clip(CircleShape)
            .background(corFundo)
            .toggleable(value = presente, role = Role.Switch, onValueChange = { onAlternar() })
            .padding(margem),
    ) {
        Box(
            Modifier
                .offset(x = deslocamento)
                .size(tamanhoBolinha)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = if (presente) {
                    Icons.Default.Check
                } else {
                    Icons.Default.Close
                },
                contentDescription = null,
                tint = corFundo,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

@Preview
@Composable
fun SwitchPresencaPreview() {
    SwitchPresenca(presente = true, onAlternar = {})
}