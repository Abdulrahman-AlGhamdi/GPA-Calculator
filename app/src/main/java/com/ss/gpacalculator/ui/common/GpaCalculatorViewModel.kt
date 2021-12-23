package com.ss.gpacalculator.ui.common

import androidx.lifecycle.ViewModel
import com.ss.gpacalculator.model.SubjectModel
import java.text.DecimalFormat

class GpaCalculatorViewModel : ViewModel() {

    var subjectList = arrayListOf(SubjectModel(number = 1, credit = 0, grade = 0))

    fun addItem(): ArrayList<SubjectModel> {
        val listSize = subjectList.size
        val newSubject = SubjectModel(number = listSize + 1, credit = 0, grade = 0)
        subjectList.add(newSubject)
        return subjectList
    }

    fun removeItem(position: Int): ArrayList<SubjectModel> {
        val subject = subjectList[position]
        subjectList.remove(subject)
        subjectList.forEach { if (it.number > position) it.number -= 1 }
        return subjectList
    }

    fun calculateSemesterGpa(): String {
        val (totalScore, totalCredit) = calculateScoreAndCredit()
        var gpa = (totalScore / totalCredit)
        if (gpa.toString() == "NaN") gpa = 0.0
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(gpa)
    }

    fun calculateOverallGpa(oldScore: Double, oldCredit: Double): String {
        val (totalScore, totalCredit) = calculateScoreAndCredit()
        var gpa = ((totalScore + oldScore) / (totalCredit + oldCredit))
        if (gpa.toString() == "NaN") gpa = 0.0
        val decimalFormat = DecimalFormat("#.##")
        return decimalFormat.format(gpa)
    }

    private fun calculateScoreAndCredit(): Pair<Double, Double> {
        var totalScore  = 0.0
        var totalCredit = 0.0

        subjectList.forEach {
            if (it.grade != 0 && it.credit != 0) {
                val credit = it.credit.toDouble()
                val grade = when (it.grade) {
                    1 -> 5.0
                    2 -> 4.75
                    3 -> 4.5
                    4 -> 4.0
                    5 -> 3.5
                    6 -> 3.0
                    7 -> 2.5
                    8 -> 2.0
                    9 -> 1.0
                    else -> 0.0
                }
                totalCredit += credit
                totalScore += credit * grade
            }
        }

        return Pair(totalScore, totalCredit)
    }
}