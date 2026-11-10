package com.nfaguai.nucleodeformacaoartistica.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.diasFormatados
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.faixaEtaria
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

/**
 * Cores usadas na faixa lateral dos cards de turma.
 * A tela escolhe uma cor pela posição da turma na lista.
 */
val CoresFaixaTurma = listOf(
    NucleoColors.Laranja,
    Color(0xFF3B4A6B), // azul-marinho
    NucleoColors.Verde,
    Color(0xFFC94A7A), // rosa
)

/**
 * Card de uma turma, como aparece na lista "Turmas de hoje".
 */
@Composable
fun CardTurma(
    turma: Turma,
    quantidadeAlunos: Int,
    corFaixa: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val forma = RoundedCornerShape(12.dp)

    // Linha 1: "Seg/Qua · 09:00 · 4 a 7 anos" (a faixa etária só entra se existir)
    val faixa = turma.faixaEtaria()
    var textoHorario = "${turma.diasSemana.diasFormatados()} · ${turma.horaInicio}"
    if (faixa.isNotEmpty()) {
        textoHorario = "$textoHorario · $faixa"
    }

    // Linha 2: "Sala 2 · 10 Alunos"
    val textoSala = "${turma.sala} · $quantidadeAlunos Alunos"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(forma)
            .background(NucleoColors.Superficie)
            .border(1.dp, NucleoColors.Borda, forma)
            .clickable(onClick = onClick)
            // Faz a linha ter a altura do conteúdo, para a faixa lateral preencher tudo
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Faixa colorida da esquerda
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(corFaixa)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text = turma.nome,
                fontFamily = FontFamily.Serif,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = NucleoColors.Texto,
            )

            Spacer(Modifier.height(6.dp))

            LinhaInfo(icone = Icons.Outlined.Schedule, texto = textoHorario)

            Spacer(Modifier.height(4.dp))

            LinhaInfo(icone = Icons.Outlined.LocationOn, texto = textoSala)
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Abrir turma",
            tint = NucleoColors.TextoSuave,
            modifier = Modifier.padding(end = 12.dp),
        )
    }
}

/**
 * Uma linha pequena com ícone + texto, usada duas vezes dentro do card.
 */
@Composable
private fun LinhaInfo(icone: ImageVector, texto: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = icone,
            contentDescription = null,
            tint = NucleoColors.TextoSuave,
            modifier = Modifier.size(12.dp),
        )
        Text(
            text = texto,
            fontSize = 11.sp,
            color = NucleoColors.TextoSuave,
        )
    }
}

@Preview
@Composable
private fun CardTurmaPreview() {
    val turma = Turma(
        id = "1",
        nome = "Guitarra / Violão",
        diasSemana = 5, // Seg + Qua
        horaInicio = "09:00",
        horaFim = "10:30",
        idadeMinima = 4,
        idadeMaxima = 7,
        professorId = "p",
        sala = "Sala 2",
    )

    Column(
        modifier = Modifier.background(NucleoColors.Fundo).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CardTurma(turma = turma, quantidadeAlunos = 10, corFaixa = CoresFaixaTurma[0], onClick = {})
        CardTurma(turma = turma.copy(nome = "Teatro", idadeMaxima = null), quantidadeAlunos = 22, corFaixa = CoresFaixaTurma[1], onClick = {})
    }
}