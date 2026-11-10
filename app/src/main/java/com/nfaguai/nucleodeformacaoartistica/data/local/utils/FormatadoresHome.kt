package com.nfaguai.nucleodeformacaoartistica.data.local.utils

import java.time.LocalDate
import java.time.LocalTime

// Funções de formatação usadas pela tela inicial (Home).
// Ficam em arquivo separado para não alterar o Formatadores.kt já existente.

private val diasCompletos = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo")
private val mesesCompletos = listOf(
    "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
    "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
)

/**
 * Data por extenso, no formato da tela inicial.
 * Exemplo: 14/09/2026 (uma segunda-feira) -> "Segunda, 14 de Setembro"
 */
fun LocalDate.formatoLongo(): String =
    "${diasCompletos[dayOfWeek.value - 1]}, $dayOfMonth de ${mesesCompletos[monthValue - 1]}"

/**
 * Saudação de acordo com a hora do dia.
 * Por padrão usa a hora atual do aparelho; o parâmetro existe
 * só para facilitar testes (ex.: saudacao(hora = 20)).
 */
fun saudacao(hora: Int = LocalTime.now().hour): String {
    return when {
        hora < 12 -> "Bom dia"
        hora < 18 -> "Boa tarde"
        else -> "Boa noite"
    }
}

/**
 * Iniciais de um nome, para o avatar.
 * Exemplo: "Virginia Fonseca" -> "VF"
 *          "Cristiane Imamura" -> "CI"
 *          "Ana" -> "A"
 */
fun String.iniciais(): String {
    // Separa o nome em palavras, ignorando espaços extras
    val palavras = trim().split(" ").filter { it.isNotBlank() }

    if (palavras.isEmpty()) {
        return ""
    }

    val primeiraLetra = palavras.first().first()

    // Se só houver uma palavra, devolve apenas a primeira letra
    if (palavras.size == 1) {
        return primeiraLetra.uppercase()
    }

    val ultimaLetra = palavras.last().first()
    return "$primeiraLetra$ultimaLetra".uppercase()
}