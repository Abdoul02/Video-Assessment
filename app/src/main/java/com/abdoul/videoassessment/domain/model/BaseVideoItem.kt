package com.abdoul.videoassessment.domain.model

interface BaseVideoItem {
    val id: String?
    val title: String?
    val subtitle: String?
    val date: Long?
    val imageUrl: String?
}