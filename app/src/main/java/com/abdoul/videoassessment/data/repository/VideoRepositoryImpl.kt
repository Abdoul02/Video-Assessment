package com.abdoul.videoassessment.data.repository

import com.abdoul.videoassessment.data.remote.VideoApi
import com.abdoul.videoassessment.data.remote.dto.ScheduleDto
import com.abdoul.videoassessment.data.remote.dto.toEventItem
import com.abdoul.videoassessment.data.remote.dto.toSchedule
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.model.ScheduleItem
import com.abdoul.videoassessment.domain.repository.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(private val api: VideoApi) : VideoRepository {

    override suspend fun getEvents(): List<EventItem> {
        return api.getEvents().map { it.toEventItem() }
    }

    override suspend fun getSchedule(): List<ScheduleItem> {
        return api.getSchedule().map { it.toSchedule() }
    }
}