package com.abdoul.videoassessment.presentation.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.use_case.GetEventsUseCase
import com.abdoul.videoassessment.presentation.VideoListSate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _events = MutableStateFlow<VideoListSate>(VideoListSate.Empty)
    val events = _events

    init {
        getEvents()
    }

    private fun getEvents() {
        getEventsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _events.value = VideoListSate.Data(result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _events.value =
                        VideoListSate.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _events.value = VideoListSate.Loading
                }
            }
        }.launchIn(viewModelScope)
    }
}