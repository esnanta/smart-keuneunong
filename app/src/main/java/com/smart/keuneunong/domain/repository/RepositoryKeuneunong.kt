package com.smart.keuneunong.domain.repository

import com.smart.keuneunong.data.model.KeuneunongPhase

interface RepositoryKeuneunong {
    fun getPhases(): List<KeuneunongPhase>
    fun getPhaseById(id: Int): KeuneunongPhase?
}