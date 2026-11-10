package com.nfaguai.nucleodeformacaoartistica

import android.app.Application
import com.nfaguai.nucleodeformacaoartistica.data.local.AppDatabase
import com.nfaguai.nucleodeformacaoartistica.data.local.DatabaseSeeder
import com.nfaguai.nucleodeformacaoartistica.data.repository.AlunoRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.ChamadaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.MatriculaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.TurmaRepository
import com.nfaguai.nucleodeformacaoartistica.data.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NucleoApp : Application() {
    val db by lazy { AppDatabase.getInstance(this) }

    val usuarioRepository by lazy { UsuarioRepository(db.usuarioDao()) }
    val alunoRepository by lazy { AlunoRepository(db.alunoDao()) }
    val turmaRepository by lazy { TurmaRepository(db.turmaDao()) }
    val matriculaRepository by lazy { MatriculaRepository(db.matriculaDao()) }
    val chamadaRepository by lazy { ChamadaRepository(db, db.aulaDao(), db.presencaDao()) }

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        appScope.launch { DatabaseSeeder(db).popular() }
    }
}