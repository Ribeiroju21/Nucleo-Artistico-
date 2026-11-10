package com.nfaguai.nucleodeformacaoartistica.data.local.utils

import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import java.time.LocalDate

private val diasAbreviados = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")
private val mesesAbreviados = listOf("jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez")

fun Int.diasFormatados(): String =
    diasAbreviados
        .filterIndexed { i, _ -> this and (1 shl i) != 0 }
        .joinToString("/")

fun Turma.faixaEtaria(): String {
    val min = idadeMinima
    val max = idadeMaxima
    return when {
        min != null && max != null -> "$min a $max anos"
        min != null -> "Acima de $min anos"
        max != null -> "Até $max anos"
        else -> ""
    }
}

fun LocalDate.formatoCurto(): String =
    "${diasAbreviados[dayOfWeek.value - 1]}, $dayOfMonth ${mesesAbreviados[monthValue - 1]}"