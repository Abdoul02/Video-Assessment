package com.abdoul.videoassessment.data.repository

import com.abdoul.videoassessment.data.remote.VideoApi
import com.abdoul.videoassessment.data.remote.dto.EventDto
import com.abdoul.videoassessment.data.remote.dto.ScheduleDto
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.model.ScheduleItem
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class VideoRepositoryImplShould {

    private val api: VideoApi = mock()
    private val eventDto = EventDto("", "", "", null, "", "")
    private val scheduleDto = ScheduleDto("", "", "", null, "")
    private val eventItem = EventItem("", "", "", null, "", "")
    private val scheduleItem = ScheduleItem("", "", "", null, "")

    @Test
    fun getEventsFromApi() = runTest {
        val repository = getRepository()

        repository.getEvents()

        verify(api, times(1)).getEvents()
    }


    @Test
    fun getScheduleFromApi() = runTest {
        val repository = getRepository()

        repository.getSchedule()

        verify(api, times(1)).getSchedule()
    }

    @Test
    fun convertEventDtoToEventItem() = runTest {
        val repository = getRepository()

        assertEquals(listOf(eventItem), repository.getEvents())
    }

    @Test
    fun convertScheduleDtoToScheduleItem() = runTest {
        val repository = getRepository()

        assertEquals(listOf(scheduleItem), repository.getSchedule())
    }

    private suspend fun getRepository(): VideoRepositoryImpl {
        whenever(api.getEvents()).thenReturn(
            listOf(eventDto)
        )
        whenever(api.getSchedule()).thenReturn(
            listOf(scheduleDto)
        )

        return VideoRepositoryImpl(api)
    }
}