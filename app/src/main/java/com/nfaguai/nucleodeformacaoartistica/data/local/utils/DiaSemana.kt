package com.nfaguai.nucleodeformacaoartistica.data.local.utils

import java.time.DayOfWeek
import java.time.LocalDate

object DiasSemana {
    const val SEG = 1
    const val TER = 2
    const val QUA = 4
    const val QUI = 8
    const val SEX = 16
    const val SAB = 32
    const val DOM = 64

    fun bitDe(dia: DayOfWeek): Int = 1 shl (dia.value - 1)

    fun hoje(): Int = bitDe(LocalDate.now().dayOfWeek)
}