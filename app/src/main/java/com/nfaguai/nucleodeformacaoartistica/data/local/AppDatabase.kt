package com.nfaguai.nucleodeformacaoartistica.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.AlunoDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.AulaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.MatriculaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.PresencaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.TurmaDao
import com.nfaguai.nucleodeformacaoartistica.data.local.dao.UsuarioDao
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aluno
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Aula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Matricula
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Presenca
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Turma
import com.nfaguai.nucleodeformacaoartistica.data.local.entity.Usuario

@Database(
    entities = [
        Usuario::class,
        Aluno::class,
        Turma::class,
        Matricula::class,
        Aula::class,
        Presenca::class
    ],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun alunoDao(): AlunoDao
    abstract fun turmaDao(): TurmaDao
    abstract fun matriculaDao(): MatriculaDao
    abstract fun aulaDao(): AulaDao
    abstract fun presencaDao(): PresencaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "nucleo.db"
                )
                    // TODO - somente em desenvolvimento para dados mockados. tirar quando tiver api
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build().also { INSTANCE = it }
            }
    }
}