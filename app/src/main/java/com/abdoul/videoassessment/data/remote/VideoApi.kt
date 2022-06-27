package com.abdoul.videoassessment.data.remote

import com.abdoul.videoassessment.data.remote.dto.EventDto
import com.abdoul.videoassessment.data.remote.dto.ScheduleDto
import retrofit2.http.GET

interface VideoApi {

    @GET("/getEvents")
    suspend fun getEvents(): List<EventDto>

    @GET("/getSchedule")
    suspend fun getSchedule(): List<ScheduleDto>
}