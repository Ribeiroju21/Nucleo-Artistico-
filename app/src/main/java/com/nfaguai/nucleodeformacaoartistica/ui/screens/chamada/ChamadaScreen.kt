package com.nfaguai.nucleodeformacaoartistica.ui.screens.chamada

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChildCare
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.diasFormatados
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.faixaEtaria
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.formatoCurto
import com.nfaguai.nucleodeformacaoartistica.ui.chamada.AlunoChamada
import com.nfaguai.nucleodeformacaoartistica.ui.chamada.ChamadaState
import com.nfaguai.nucleodeformacaoartistica.ui.chamada.ChamadaViewModel
import com.nfaguai.nucleodeformacaoartistica.ui.components.Aba
import com.nfaguai.nucleodeformacaoartistica.ui.components.BarraNavegacao
import com.nfaguai.nucleodeformacaoartistica.ui.components.BotaoPrimario
import com.nfaguai.nucleodeformacaoartistica.ui.components.BotaoVoltar
import com.nfaguai.nucleodeformacaoartistica.ui.components.CampoBusca
import com.nfaguai.nucleodeformacaoartistica.ui.components.Card
import com.nfaguai.nucleodeformacaoartistica.ui.components.InfoChip
import com.nfaguai.nucleodeformacaoartistica.ui.components.Item
import com.nfaguai.nucleodeformacaoartistica.ui.theme.NucleoColors

@Composable
fun ChamadaRoute(
    turmaId: String,
    usuarioId: String,
    onVoltar: () -> Unit,
    onSelecionarAba: (Aba) -> Unit,
) {
    val viewModel: ChamadaViewModel = viewModel(
        factory = ChamadaViewModel.factory(turmaId, usuarioId)
    )
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChamadaScreen(
        state = state,
        onVoltar = onVoltar,
        onBuscaChange = viewModel::alterarBusca,
        onAlternar = viewModel::alternarPresenca,
        onSalvar = viewModel::salvar,
        onMensagemExibida = viewModel::mensagemExibida,
        onSelecionarAba = onSelecionarAba,
    )
}

@Composable
fun ChamadaScreen(
    state: ChamadaState,
    onVoltar: () -> Unit,
    onBuscaChange: (String) -> Unit,
    onAlternar: (String) -> Unit,
    onSalvar: () -> Unit,
    onMensagemExibida: () -> Unit,
    onSelecionarAba: (Aba) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.mensagem) {
        val msg = state.mensagem
        if (msg != null) {
            snackbarHostState.showSnackbar(msg)
            onMensagemExibida()
        }
    }

    Scaffold(
        containerColor = NucleoColors.Fundo,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CabecalhoChamada(
                state = state,
                onVoltar = onVoltar,
            )
        },
        bottomBar = {
            Column {
                BotaoPrimario(
                    texto = if (state.chamadaJaRegistrada) {
                        "Atualizar Chamada"
                    } else {
                        "Salvar Chamada"
                    },
                    onClick = onSalvar,
                    carregando = state.salvando,
                    enabled = !state.carregando && state.alunos.isNotEmpty(),
                    modifier = Modifier
                        .background(NucleoColors.Fundo)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                )
                BarraNavegacao(
                    abaAtual = Aba.CHAMADA,
                    onSelecionar = onSelecionarAba,
                )
            }
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
            ListaChamada(
                state = state,
                padding = padding,
                onBuscaChange = onBuscaChange,
                onAlternar = onAlternar,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CabecalhoChamada(
    state: ChamadaState,
    onVoltar: () -> Unit,
) {
    val turma = state.turma

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NucleoColors.Superficie)
            .statusBarsPadding()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                BotaoVoltar(onClick = onVoltar)
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Chamada • ${state.data.formatoCurto()}",
                    fontSize = 11.sp,
                    color = NucleoColors.Laranja,
                    modifier = Modifier
                        .background(NucleoColors.LaranjaClaro, CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = turma?.nome.orEmpty(),
                fontFamily = FontFamily.Serif,
                fontSize = 24.sp,
                color = NucleoColors.Texto,
            )

            Spacer(Modifier.height(8.dp))

            if (turma != null) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    InfoChip(Icons.Outlined.Schedule, "${turma.diasSemana.diasFormatados()} • ${turma.horaInicio}")
                    InfoChip(Icons.Outlined.LocationOn, turma.sala)
                    InfoChip(Icons.Outlined.Group, "${state.total} alunos")

                    val faixa = turma.faixaEtaria()
                    if (faixa.isNotEmpty()) {
                        InfoChip(Icons.Outlined.ChildCare, faixa)
                    }
                }
            }
        }
        HorizontalDivider(color = NucleoColors.Borda)
    }
}

@Composable
private fun ListaChamada(
    state: ChamadaState,
    padding: PaddingValues,
    onBuscaChange: (String) -> Unit,
    onAlternar: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = padding.calculateTopPadding() + 16.dp,
            bottom = padding.calculateBottomPadding() + 16.dp,
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(state.presentes, "presentes", NucleoColors.VerdeClaro, NucleoColors.Verde, Modifier.weight(1f))
                Card(state.faltas, "faltas", NucleoColors.VermelhoClaro, NucleoColors.Vermelho, Modifier.weight(1f))
                Card(state.total, "total", NucleoColors.Superficie, NucleoColors.Texto, Modifier.weight(1f), corBorda = NucleoColors.Borda)
            }
        }

        item {
            Text(
                text = "Alunos",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = NucleoColors.Texto,
                modifier = Modifier.padding(top = 8.dp),
            )
        }

        item {
            CampoBusca(
                valor = state.busca,
                onValorChange = onBuscaChange,
                placeholder = "Buscar aluno",
            )
        }

        item {
            Text(
                text = "Use o interruptor para marcar presença ou falta de cada aluno",
                fontSize = 12.sp,
                color = NucleoColors.TextoSuave,
            )
        }

        if (state.alunosFiltrados.isEmpty()) {
            item {
                Text(
                    text = if (state.alunos.isEmpty()) {
                        "Nenhum aluno matriculado nesta turma"
                    } else {
                        "Nenhum aluno encontrado"
                    },
                    color = NucleoColors.TextoSuave,
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }

        items(state.alunosFiltrados, key = { it.id }) { aluno ->
            Item(
                nome = aluno.nome,
                presente = aluno.presente,
                onAlternar = { onAlternar(aluno.id) },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ChamadaScreenPreview() {
    ChamadaScreen(
        state = ChamadaState(
            carregando = false,
            turma = Turma(
                id = "1", nome = "Guitarra / Violão", diasSemana = 5,
                horaInicio = "09:00", horaFim = "10:30", idadeMinima = 4, idadeMaxima = 7,
                professorId = "p", sala = "Sala 2",
            ),
            alunos = listOf(
                AlunoChamada("1", "Maria Eduarda Campos", true),
                AlunoChamada("2", "Lorena Marques", false),
                AlunoChamada("3", "Caio Eduardo Carvalho", false),
                AlunoChamada("4", "Juliana Gonçalves Ribeiro", false),
            ),
        ),
        onVoltar = {}, onBuscaChange = {}, onAlternar = {}, onSalvar = {},
        onMensagemExibida = {}, onSelecionarAba = {},
    )
}