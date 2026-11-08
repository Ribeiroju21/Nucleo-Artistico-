package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun Card(
    valor: Int,
    rotulo: String,
    corFundo: Color,
    corTexto: Color,
    modifier: Modifier = Modifier,
    corBorda: Color? = null,
) {
    val forma = RoundedCornerShape(8.dp)

    var base = modifier
        .height(72.dp)
        .background(corFundo, forma)

    if (corBorda != null) {
        base = base.border(1.dp, corBorda, forma)
    }

    Column(
        modifier = base.padding(horizontal = 12.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = valor.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = corTexto,
        )
        Text(
            text = rotulo,
            fontSize = 12.sp,
            color = corTexto,
        )
    }
}

@Preview
@Composable
private fun Card() {
    Row(
        modifier = Modifier.background(NucleoColors.Fundo).padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Card(3, "presentes", NucleoColors.VerdeClaro, NucleoColors.Verde, Modifier.weight(1f))
        Card(1, "faltas", NucleoColors.VermelhoClaro, NucleoColors.Vermelho, Modifier.weight(1f))
        Card(4, "total", NucleoColors.Superficie, NucleoColors.Texto, Modifier.weight(1f), corBorda = NucleoColors.Borda)
    }
}