package com.abdoul.videoassessment.presentation.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.use_case.GetScheduleUseCase
import com.abdoul.videoassessment.presentation.VideoListSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getScheduleUseCase: GetScheduleUseCase
) : ViewModel() {

    private val _schedule = MutableStateFlow<VideoListSate>(VideoListSate.Empty)
    val schedule = _schedule
    lateinit var timer: Timer

    init {
        getSchedule()
        refresh()
    }

    private fun getSchedule() {
        getScheduleUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _schedule.value = VideoListSate.Data(result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _schedule.value =
                        VideoListSate.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _schedule.value = VideoListSate.Loading
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun refresh() {
        timer = fixedRateTimer(TIMER_NAME, false, TIMER_DELAY, TIMER_DELAY) {
            getSchedule()
        }
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    companion object {
        private const val TIMER_NAME = "schedule_refresh"
        private const val TIMER_DELAY = 30_000L
    }

}