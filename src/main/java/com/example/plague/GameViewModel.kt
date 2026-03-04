package com.example.plague

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GameState(
    val gameStarted: Boolean = false,
    val diseaseName: String = "",
    val difficulty: String = "Средний",
    val dnaPoints: Int = 0,
    val infectedCount: Long = 0L,
    val cureProgress: Float = 0f,
    val clicksPerClick: Int = 1,
    val infectedPerSecond: Int = 0,
    val cureResistance: Int = 0,
    val elapsedSeconds: Long = 0L,
    val clickUpgradeCount: Int = 0,
    val secondUpgradeCount: Int = 0,
    val cureResistanceCount: Int = 0,
    val isGamePaused: Boolean = false,
    val isTimerRunning: Boolean = false,
)

class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    private var timerJob: Job? = null

    fun startNewGame(name: String, difficulty: String) {
        val multiplier = when (difficulty) {
            "Лёгкий" -> 1
            "Сложный" -> 2
            else -> 1
        }
        _state.value = GameState(
            gameStarted = true,
            diseaseName = name,
            difficulty = difficulty,
            dnaPoints = 0,
            infectedCount = 0L,
            cureProgress = 0f,
            clicksPerClick = 1,
            infectedPerSecond = multiplier,
            cureResistance = 0,
            elapsedSeconds = 0L,
            isTimerRunning = true,
        )
        startTimer()
    }

    fun resumeGame() {
        _state.value = _state.value.copy(isGamePaused = false, isTimerRunning = true)
        startTimer()
    }

    fun pauseGame() {
        _state.value = _state.value.copy(isGamePaused = true, isTimerRunning = false)
        timerJob?.cancel()
    }

    fun addClick() {
        val s = _state.value
        val newDna = s.dnaPoints + s.clicksPerClick
        val newInfected = s.infectedCount + s.clicksPerClick
        _state.value = s.copy(dnaPoints = newDna, infectedCount = newInfected)
    }

    fun buyClickUpgrade(): Boolean {
        val s = _state.value
        if (s.dnaPoints < 50) return false
        _state.value = s.copy(
            dnaPoints = s.dnaPoints - 50,
            clicksPerClick = s.clicksPerClick + 1,
            clickUpgradeCount = s.clickUpgradeCount + 1
        )
        return true
    }

    fun buySecondUpgrade(): Boolean {
        val s = _state.value
        if (s.dnaPoints < 50) return false
        _state.value = s.copy(
            dnaPoints = s.dnaPoints - 50,
            infectedPerSecond = s.infectedPerSecond + 1,
            secondUpgradeCount = s.secondUpgradeCount + 1
        )
        return true
    }

    fun buyCureResistance(): Boolean {
        val s = _state.value
        if (s.dnaPoints < 50) return false
        _state.value = s.copy(
            dnaPoints = s.dnaPoints - 50,
            cureResistance = s.cureResistance + 1,
            cureResistanceCount = s.cureResistanceCount + 1
        )
        return true
    }

    fun resetGame() {
        timerJob?.cancel()
        _state.value = GameState()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                val s = _state.value
                if (!s.isTimerRunning) break
                val newInfected = s.infectedCount + s.infectedPerSecond
                val cureIncrease = if (s.cureResistance > 0) 0.001f else 0.002f
                val newCure = minOf(1f, s.cureProgress + cureIncrease)
                _state.value = s.copy(
                    elapsedSeconds = s.elapsedSeconds + 1,
                    infectedCount = newInfected,
                    cureProgress = newCure
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
