package com.abdoul.videoassessment.domain.repository

import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.model.ScheduleItem

interface VideoRepository {

    suspend fun getEvents(): List<EventItem>

    suspend fun getSchedule(): List<ScheduleItem>
}