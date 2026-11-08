package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

enum class Aba(val rotulo: String, val icone: ImageVector) {
    HOME("Home", Icons.Outlined.Home),
    CHAMADA("Chamada", Icons.Outlined.Checklist),
    TURMAS("Turmas", Icons.Outlined.GridView),
    AVISOS("Notificações", Icons.Outlined.NotificationsNone),
}

@Composable
fun BarraNavegacao(abaAtual: Aba, onSelecionar: (Aba) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier.background(NucleoColors.Superficie).navigationBarsPadding()) {
        HorizontalDivider(color = NucleoColors.Borda)
        Row(Modifier.fillMaxWidth().height(64.dp)) {
            Aba.entries.forEach { aba ->
                val selecionada = aba == abaAtual
                val cor = if (selecionada) {
                    NucleoColors.Laranja
                } else {
                    NucleoColors.TextoSuave
                }
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight().clickable { onSelecionar(aba) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier.width(40.dp).height(2.dp)
                            .background(if (selecionada) {
                                NucleoColors.Laranja
                            } else {
                                Color.Transparent
                            })
                    )
                    Spacer(Modifier.height(10.dp))
                    Icon(aba.icone, contentDescription = null, tint = cor)
                    Text(aba.rotulo, fontSize = 11.sp, color = cor)
                }
            }
        }
    }
}

@Preview
@Composable
private fun BarraNavegacaoPreview() {
    BarraNavegacaoPreview()
}