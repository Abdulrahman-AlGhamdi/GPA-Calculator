package com.ss.gpacalculator.repository.overall

import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.DecimalFormat
import javax.inject.Inject

class OverallRepositoryImpl @Inject constructor() : OverallRepository {

    private var subjectList = arrayListOf(SubjectModel())

    override fun getList(): Flow<State<List<SubjectModel>>> = flow {
        this.emit(State.Success(subjectList))
    }

    override fun addSubject(): Flow<State<List<SubjectModel>>> = flow {
        val listSize = subjectList.size
        val newSubject = SubjectModel(number = listSize + 1, credit = ' ', grade = "")
        subjectList.add(newSubject)
        this.emit(State.Success(subjectList))
    }

    override fun removeSubject(position: Int): Flow<State<List<SubjectModel>>> = flow {
        val subject = subjectList[position]
        subjectList.remove(subject)
        subjectList.forEach { if (it.number > position) it.number -= 1 }
        this.emit(State.Success(subjectList))
    }

    override fun calculateOverallGpa(previousGpa: String, previousCredit: String): String {
        val prevCredit = previousCredit.toDouble()
        val prevScore = previousGpa.toDouble() * prevCredit
        val (totalScore, totalCredit) = calculateScoreAndCredit()
        var gpa = ((totalScore + prevScore) / (totalCredit + prevCredit))
        if (gpa.toString() == "NaN") gpa = 0.0
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(gpa)
    }

    private fun calculateScoreAndCredit(): Pair<Double, Double> {
        var totalScore = 0.0
        var totalCredit = 0.0

        for (subject in subjectList) {
            if (!subject.credit.isDigit()) continue
            val credit = subject.credit.digitToInt()

            val grade = when (subject.grade) {
                "A+" -> 5.0
                "A" -> 4.75
                "B+" -> 4.5
                "B" -> 4.0
                "C+" -> 3.5
                "C" -> 3.0
                "D+" -> 2.5
                "D" -> 2.0
                "F" -> 1.0
                else -> 0.0
            }

            totalCredit += credit
            totalScore += credit * grade
        }

        return Pair(totalScore, totalCredit)
    }
}