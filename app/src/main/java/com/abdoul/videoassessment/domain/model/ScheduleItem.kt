package com.abdoul.videoassessment.domain.model

data class ScheduleItem(
    override val id: String?,
    override val title: String?,
    override val subtitle: String?,
    override val date: Long?,
    override val imageUrl: String?
) : BaseVideoItem
