package com.ss.gpacalculator.ui

import androidx.lifecycle.ViewModel
import com.ss.gpacalculator.model.SubjectModel

class CalculateViewModel : ViewModel() {

    var semesterSubjectList = ArrayList<SubjectModel>()
    var overallSubjectList = ArrayList<SubjectModel>()
}