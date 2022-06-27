package com.abdoul.videoassessment.domain.use_case

import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.repository.VideoRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class GetEventUseCaseShould {

    private val repository: VideoRepository = mock()
    private val eventItem = EventItem("", "", "", null, "", "")
    private val httpException: HttpException = mock()

    @Test
    fun emitLoadingState() = runTest {
        val useCase = getSuccessEventUseCase()

        assert(useCase().first() is Resource.Loading)
    }

    @Test
    fun emitSuccessState() = runTest {
        val useCase = getSuccessEventUseCase()

        assert(useCase().last() is Resource.Success)
    }

    @Test
    fun emitErrorStateHttpException() = runTest {
        val useCase = getErrorEventUseCase()

        assert(useCase().last() is Resource.Error)
    }


    private suspend fun getSuccessEventUseCase(): GetEventsUseCase {
        whenever(repository.getEvents()).thenReturn(
            listOf(eventItem)
        )

        return GetEventsUseCase(repository)
    }

    private suspend fun getErrorEventUseCase(): GetEventsUseCase {
        whenever(repository.getEvents()).thenThrow(httpException)

        return GetEventsUseCase(repository)
    }
}