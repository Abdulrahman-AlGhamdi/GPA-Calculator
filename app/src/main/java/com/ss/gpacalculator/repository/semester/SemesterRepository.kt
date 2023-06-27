package com.ss.gpacalculator.repository.semester

import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.utils.State
import kotlinx.coroutines.flow.Flow

interface SemesterRepository {

    fun getList(): Flow<State<List<SubjectModel>>>

    fun addSubject(): Flow<State<List<SubjectModel>>>

    fun removeSubject(position: Int): Flow<State<List<SubjectModel>>>

    fun calculateSemesterGpa(): String
}