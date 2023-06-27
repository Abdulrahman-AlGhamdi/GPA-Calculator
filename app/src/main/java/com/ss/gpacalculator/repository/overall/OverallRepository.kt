package com.ss.gpacalculator.repository.overall

import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.utils.State
import kotlinx.coroutines.flow.Flow

interface OverallRepository {

    fun getList(): Flow<State<List<SubjectModel>>>

    fun addSubject(): Flow<State<List<SubjectModel>>>

    fun removeSubject(position: Int): Flow<State<List<SubjectModel>>>

    fun calculateOverallGpa(previousGpa: String, previousCredit: String): String
}