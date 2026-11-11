    package com.nfaguai.nucleodeformacaoartistica.data.local.entity

    import androidx.room.ColumnInfo
    import androidx.room.Entity
    import androidx.room.ForeignKey
    import androidx.room.Index
    import androidx.room.PrimaryKey
    import java.util.UUID

    @Entity(
        tableName = "presencas",
        primaryKeys = ["id"],
        foreignKeys = [
            ForeignKey(
                entity = Aula::class,
                parentColumns = ["id"],
                childColumns = ["aula_id"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = Aluno::class,
                parentColumns = ["id"],
                childColumns = ["aluno_id"],
                onDelete = ForeignKey.RESTRICT
            )
        ],
        indices = [
            Index(value = ["aula_id", "aluno_id"], unique = true),
            Index(value = ["aluno_id"])
        ]
    )
    data class Presenca(
        val id: String = UUID.randomUUID().toString(),
        @ColumnInfo(name = "aula_id") val aulaId: String,
        @ColumnInfo(name = "aluno_id") val alunoId: String,
        val presente: Boolean
    )