package com.nfaguai.nucleodeformacaoartistica.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.formatoLongo
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.iniciais
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.saudacao
import com.nfaguai.nucleodeformacaoartistica.ui.components.Aba
import com.nfaguai.nucleodeformacaoartistica.ui.components.BarraNavegacao
import com.nfaguai.nucleodeformacaoartistica.ui.components.Card
import com.nfaguai.nucleodeformacaoartistica.ui.components.CardTurma
import com.nfaguai.nucleodeformacaoartistica.ui.components.CoresFaixaTurma
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

/**
 * Ponto de entrada da tela inicial: cria o ViewModel, observa o estado
 * e repassa para a HomeScreen. Mesmo papel da ChamadaRoute.
 */
@Composable
fun HomeRoute(
    usuarioId: String,
    onAbrirTurma: (String) -> Unit,
    onSelecionarAba: (Aba) -> Unit,
) {
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.factory(usuarioId)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAbrirTurma = onAbrirTurma,
        onSelecionarAba = onSelecionarAba,
    )
}

/**
 * A tela em si. Só desenha o que está no HomeState.
 */
@Composable
fun HomeScreen(
    state: HomeState,
    onAbrirTurma: (String) -> Unit,
    onSelecionarAba: (Aba) -> Unit,
) {
    Scaffold(
        containerColor = NucleoColors.Fundo,
        topBar = {
            CabecalhoHome(state = state)
        },
        bottomBar = {
            BarraNavegacao(
                abaAtual = Aba.HOME,
                onSelecionar = onSelecionarAba,
            )
        },
    ) { padding ->
        if (state.carregando) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = NucleoColors.Laranja)
            }
        } else {
            ConteudoHome(
                state = state,
                padding = padding,
                onAbrirTurma = onAbrirTurma,
            )
        }
    }
}

/**
 * Cabeçalho branco: saudação, data por extenso e avatar com as iniciais.
 */
@Composable
private fun CabecalhoHome(state: HomeState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NucleoColors.Superficie)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Linha pequena: sol + "Boa tarde, Prof. Nome"
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.WbSunny,
                        contentDescription = null,
                        tint = NucleoColors.TextoSuave,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${saudacao()}, Prof. ${state.nomeProfessor}",
                        fontSize = 12.sp,
                        color = NucleoColors.TextoSuave,
                    )
                }

                Spacer(Modifier.height(4.dp))

                // Data por extenso, em serifa
                Text(
                    text = state.data.formatoLongo(),
                    fontFamily = FontFamily.Serif,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = NucleoColors.Texto,
                )
            }

            // Avatar redondo com as iniciais
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(NucleoColors.LaranjaClaro, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = state.nomeProfessor.iniciais(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NucleoColors.Laranja,
                )
            }
        }
        HorizontalDivider(color = NucleoColors.Borda)
    }
}

/**
 * Corpo da tela: os dois cards de resumo + a lista de turmas de hoje.
 */
@Composable
private fun ConteudoHome(
    state: HomeState,
    padding: PaddingValues,
    onAbrirTurma: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = padding.calculateTopPadding() + 16.dp,
            bottom = padding.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        // Cards de resumo (reaproveita o componente Card da tela de Chamada)
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(
                    valor = state.totalTurmasHoje,
                    rotulo = "turmas hoje",
                    corFundo = NucleoColors.LaranjaClaro,
                    corTexto = NucleoColors.Laranja,
                    modifier = Modifier.weight(1f),
                )
                Card(
                    valor = state.totalAlunosAtivos,
                    rotulo = "alunos ativos",
                    corFundo = NucleoColors.VerdeClaro,
                    corTexto = NucleoColors.Verde,
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item {
            Text(
                text = "Turmas de hoje",
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = NucleoColors.Texto,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        if (state.turmasDeHoje.isEmpty()) {
            item {
                Text(
                    text = "Nenhuma turma para hoje",
                    color = NucleoColors.TextoSuave,
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

        // itemsIndexed entrega a posição (indice) junto com o item,
        // e a posição define a cor da faixa lateral
        itemsIndexed(state.turmasDeHoje, key = { _, item -> item.turma.id }) { indice, item ->
            CardTurma(
                turma = item.turma,
                quantidadeAlunos = item.quantidadeAlunos,
                corFaixa = CoresFaixaTurma[indice % CoresFaixaTurma.size],
                onClick = { onAbrirTurma(item.turma.id) },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val violao = Turma(
        id = "1", nome = "Guitarra / Violão", diasSemana = 5,
        horaInicio = "09:00", horaFim = "10:30", idadeMinima = 4, idadeMaxima = 7,
        professorId = "p", sala = "Sala 2",
    )
    val teatro = Turma(
        id = "2", nome = "Teatro", diasSemana = 1,
        horaInicio = "21:00", horaFim = "22:30", idadeMinima = 12,
        professorId = "p", sala = "Sala 1",
    )
    val circo = Turma(
        id = "3", nome = "Circo", diasSemana = 5,
        horaInicio = "10:30", horaFim = "12:00", idadeMinima = 4, idadeMaxima = 7,
        professorId = "p", sala = "Sala 2",
    )
    val danca = Turma(
        id = "4", nome = "Dança de Salão/Samba", diasSemana = 1,
        horaInicio = "10:30", horaFim = "12:00", idadeMinima = 12,
        professorId = "p", sala = "Sala 1",
    )
    val desenho = Turma(
        id = "5", nome = "Desenho", diasSemana = 9,
        horaInicio = "19:00", horaFim = "20:30", idadeMinima = 10,
        professorId = "p", sala = "Sala 3",
    )

    HomeScreen(
        state = HomeState(
            carregando = false,
            nomeProfessor = "Virginia Fonseca",
            turmasDeHoje = listOf(
                TurmaHome(violao, 10),
                TurmaHome(teatro, 22),
                TurmaHome(circo, 9),
                TurmaHome(danca, 13),
                TurmaHome(desenho, 25),
            ),
            totalAlunosAtivos = 12,
        ),
        onAbrirTurma = {},
        onSelecionarAba = {},
    )
}