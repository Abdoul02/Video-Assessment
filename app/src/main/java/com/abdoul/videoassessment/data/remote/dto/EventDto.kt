package com.abdoul.videoassessment.data.remote.dto

import com.abdoul.videoassessment.common.DateConversion.toLongDate
import com.abdoul.videoassessment.domain.model.EventItem

data class EventDto(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val date: String?,
    val imageUrl: String?,
    val videoUrl: String?
)

fun EventDto.toEventItem(): EventItem {
    return EventItem(
        id = id,
        title = title,
        subtitle = subtitle,
        date = date?.toLongDate(),
        imageUrl = imageUrl,
        videoUrl = videoUrl
    )
}
