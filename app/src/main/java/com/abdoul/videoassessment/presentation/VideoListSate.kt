package com.abdoul.videoassessment.presentation

import com.abdoul.videoassessment.domain.model.BaseVideoItem

sealed class VideoListSate {
    object Empty : VideoListSate()
    object Loading : VideoListSate()
    data class Data(val videoItems: List<BaseVideoItem>) : VideoListSate()
    data class Error(val errorMsg: String) : VideoListSate()
}
