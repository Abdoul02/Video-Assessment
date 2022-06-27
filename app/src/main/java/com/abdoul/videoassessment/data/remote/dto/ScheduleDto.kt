package com.abdoul.videoassessment.data.remote.dto

import com.abdoul.videoassessment.common.DateConversion.toLongDate
import com.abdoul.videoassessment.domain.model.ScheduleItem

data class ScheduleDto(
    val id: String?,
    val title: String?,
    val subtitle: String?,
    val date: String?,
    val imageUrl: String?
)

fun ScheduleDto.toSchedule(): ScheduleItem {
    return ScheduleItem(
        id = id,
        title = title,
        subtitle = subtitle,
        date = date?.toLongDate(),
        imageUrl = imageUrl,
    )
}
