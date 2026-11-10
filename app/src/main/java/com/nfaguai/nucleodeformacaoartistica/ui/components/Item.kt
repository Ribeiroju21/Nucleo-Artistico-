package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun Item(
    nome: String,
    presente: Boolean,
    onAlternar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val forma = RoundedCornerShape(8.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(NucleoColors.Superficie, forma)
            .border(1.dp, NucleoColors.Borda, forma)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = nome,
                fontSize = 14.sp,
                color = NucleoColors.Texto,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = if (presente) {
                    "Presente"
                } else {
                    "Falta"
                },
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (presente) {
                    NucleoColors.Verde
                } else {
                    NucleoColors.Vermelho
                },
            )
        }
        SwitchPresenca(presente = presente, onAlternar = onAlternar)
    }
}

@Preview
@Composable
private fun Item() {
    var presente by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.background(NucleoColors.Fundo).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Item("Maria Eduarda Campos", presente, onAlternar = { presente = !presente })
        Item("Juliana Gonçalves Ribeiro de Albuquerque", false, onAlternar = {})
    }
}