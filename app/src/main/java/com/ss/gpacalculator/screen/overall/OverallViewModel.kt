package com.ss.gpacalculator.screen.overall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ss.gpacalculator.model.SubjectModel
import com.ss.gpacalculator.repository.overall.OverallRepository
import com.ss.gpacalculator.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverallViewModel @Inject constructor(
    private val overallRepository: OverallRepository
) : ViewModel() {

    private val _overallState = MutableStateFlow<State<List<SubjectModel>>>(State.Idle())
    val overallState = _overallState.asStateFlow()

    init {
        getList()
    }

    private fun getList() = viewModelScope.launch {
        overallRepository.getList().collect {
            _overallState.value = it
        }
    }

    fun addSubject() = viewModelScope.launch {
        overallRepository.addSubject().collect {
            _overallState.value = it
        }
    }

    fun removeSubject(position: Int) = viewModelScope.launch {
        overallRepository.removeSubject(position).collect {
            _overallState.value = it
        }
    }

    fun calculateOverallGpa(previousGpa: String, previousCredit: String): String {
        return overallRepository.calculateOverallGpa(previousGpa, previousCredit)
    }
}