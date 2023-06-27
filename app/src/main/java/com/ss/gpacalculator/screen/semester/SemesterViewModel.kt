package com.ss.gpacalculator.screen.semester

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.repository.semester.SemesterRepository
import com.ss.gpacalculator.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SemesterViewModel @Inject constructor(
    private val semesterRepository: SemesterRepository
) : ViewModel() {

    private val _semesterState = MutableStateFlow<State<List<SubjectModel>>>(State.Idle())
    val semesterState = _semesterState.asStateFlow()

    init {
        getList()
    }

    private fun getList() = viewModelScope.launch {
        semesterRepository.getList().collect {
            _semesterState.value = it
        }
    }

    fun addSubject() = viewModelScope.launch {
        semesterRepository.addSubject().collect {
            _semesterState.value = it
        }
    }

    fun removeSubject(position: Int) = viewModelScope.launch {
        semesterRepository.removeSubject(position).collect {
            _semesterState.value = it
        }
    }

    fun calculateSemesterGpa(): String {
        return semesterRepository.calculateSemesterGpa()
    }
}