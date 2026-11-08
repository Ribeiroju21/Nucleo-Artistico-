package com.nfaguai.nucleodeformacaoartistica.data.local

import androidx.room.withTransaction
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Matricula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Presenca
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Usuario
import com.nfaguai.nucleodeformacaoartistica.data.local.utils.DiasSemana
import java.time.LocalDate
import kotlin.random.Random

class DatabaseSeeder(private val db: AppDatabase) {

    suspend fun popular() {
        if (db.usuarioDao().contar() > 0) {
            return
        }

        db.withTransaction {
            db.usuarioDao().salvarTodos(usuarios)
            db.turmaDao().salvarTodas(turmas)
            db.alunoDao().salvarTodos(alunos)

            val matriculas = matriculas()
            db.matriculaDao().salvarTodas(matriculas)

            popularHistorico(matriculas)
        }
    }

    private val professora = Usuario(
        id = "prof-cris",
        nome = "Cristiane Imamura",
        email = "cristiane@nucleo.com",
        senha = "123456",
        funcao = "professor"
    )

    private val usuarios = listOf(professora)

    private val turmas = listOf(
        Turma(
            id = "tur-violao",
            nome = "Guitarra / Violão",
            diasSemana = DiasSemana.SEG or DiasSemana.QUA,
            horaInicio = "09:00",
            horaFim = "10:30",
            idadeMinima = 12,
            sala = "Sala 2",
            professorId = professora.id
        ),
        Turma(
            id = "tur-teatro",
            nome = "Teatro",
            diasSemana = DiasSemana.SEG,
            horaInicio = "09:00",
            horaFim = "10:30",
            idadeMinima = 12,
            sala = "Sala 1",
            professorId = professora.id
        ),
        Turma(
            id = "tur-circo",
            nome = "Circo",
            diasSemana = DiasSemana.SEG or DiasSemana.QUA,
            horaInicio = "10:30",
            horaFim = "12:00",
            idadeMinima = 4,
            idadeMaxima = 7,
            sala = "Sala 2",
            professorId = professora.id
        ),
        Turma(
            id = "tur-danca",
            nome = "Dança de Salão/Samba",
            diasSemana = DiasSemana.SEG,
            horaInicio = "10:30",
            horaFim = "12:00",
            idadeMinima = 12,
            sala = "Sala 1",
            professorId = professora.id
        ),
        Turma(
            id = "tur-desenho",
            nome = "Desenho",
            diasSemana = DiasSemana.SEG or DiasSemana.QUA,
            horaInicio = "19:00",
            horaFim = "20:30",
            idadeMinima = 10,
            sala = "Sala 3",
            professorId = professora.id
        ),
        Turma(
            id = "tur-yoga",
            nome = "Yoga",
            diasSemana = DiasSemana.SEG or DiasSemana.QUI,
            horaInicio = "19:00",
            horaFim = "20:30",
            idadeMinima = 10,
            sala = "Sala 3",
            professorId = professora.id
        ),
        Turma(
            id = "tur-capoeira",
            nome = "Capoeira Angola",
            diasSemana = DiasSemana.TER or DiasSemana.QUI,
            horaInicio = "18:00",
            horaFim = "19:30",
            idadeMinima = 8,
            sala = "Sala 1",
            professorId = professora.id
        ),
        Turma(
            id = "tur-banda",
            nome = "Banda Musical",
            diasSemana = DiasSemana.TER or DiasSemana.SEX,
            horaInicio = "19:00",
            horaFim = "20:30",
            idadeMinima = 12,
            sala = "Sala 2",
            professorId = professora.id
        )
    )

    private val alunos = listOf(
        "Maria Eduarda Campos",
        "Lorena Marques",
        "Caio Eduardo Carvalho",
        "Juliana Gonçalves Ribeiro",
        "Ana Beatriz de Souza Lima",
        "Bruno Cardoso Lima",
        "Camila Ferreira da Silva Batista",
        "Davi Henrique Alves",
        "Felipe Augusto Dias da Rocha",
        "João Vitor Barbosa",
        "Gabriela Nogueira",
        "Miguel Torres",
        "Isabela Martins",
        "Lucas Pereira Santos",
        "Mariana Costa Oliveira",
        "Pedro Henrique Souza",
        "Rafaela Almeida",
        "Thiago Rodrigues",
        "Larissa Mendes",
        "Gustavo Ribeiro",
        "Beatriz Fernandes",
        "Matheus Araújo",
        "Letícia Cardoso",
        "Enzo Gabriel Lopes",
        "Sofia Carvalho",
        "Arthur Moreira",
        "Valentina Rocha",
        "Heitor Gomes",
        "Helena Barros",
        "Lorenzo Pinto",
        "Alice Teixeira",
        "Samuel Correia",
        "Laura Monteiro",
        "Nicolas Freitas",
        "Manuela Castro",
        "Benjamin Duarte",
        "Cecília Ramos",
        "Theo Vieira",
        "Lívia Nunes",
        "Rafael Moura"
    ).mapIndexed { i, nome -> Aluno(id = "alu-${i + 1}", nome = nome) }

    private val alunosPorTurma = mapOf(
        "tur-violao" to 0..9,
        "tur-teatro" to 4..25,
        "tur-circo" to 26..34,
        "tur-danca" to 10..22,
        "tur-desenho" to 15..39,
        "tur-yoga" to 0..24,
        "tur-capoeira" to 5..16,
        "tur-banda" to 20..31
    )

    private fun matriculas(): List<Matricula> =
        alunosPorTurma.flatMap { (turmaId, faixa) ->
            alunos.slice(faixa).map { aluno ->
                Matricula(
                    id = "mat-$turmaId-${aluno.id}",
                    alunoId = aluno.id,
                    turmaId = turmaId
                )
            }
        }

    private suspend fun popularHistorico(matriculas: List<Matricula>) {
        val random = Random(42)
        val hoje = LocalDate.now()
        val alunosDaTurma = matriculas.groupBy({ it.turmaId }, { it.alunoId })

        for (diasAtras in 28 downTo 1) {
            val data = hoje.minusDays(diasAtras.toLong())
            val bitDoDia = DiasSemana.bitDe(data.dayOfWeek)

            turmas.filter { it.diasSemana and bitDoDia != 0 }.forEach { turma ->
                val aula = Aula(
                    id = "aula-${turma.id}-$data",
                    data = data.toString(),
                    turmaId = turma.id,
                    usuarioId = professora.id
                )
                db.aulaDao().inserir(aula)

                val presencas = alunosDaTurma[turma.id].orEmpty().map { alunoId ->
                    Presenca(
                        id = "pre-${aula.id}-$alunoId",
                        aulaId = aula.id,
                        alunoId = alunoId,
                        presente = random.nextInt(100) < 80
                    )
                }
                db.presencaDao().salvarTodas(presencas)
            }
        }
    }
}
