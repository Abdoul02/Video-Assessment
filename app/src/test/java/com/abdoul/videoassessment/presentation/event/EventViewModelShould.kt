package com.abdoul.videoassessment.presentation.event

import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.use_case.GetEventsUseCase
import com.abdoul.videoassessment.presentation.VideoListSate
import com.abdoul.videoassessment.util.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class EventViewModelShould : BaseUnitTest() {

    private val getEventsUseCase: GetEventsUseCase = mock()
    private val eventItem = EventItem("", "", "", null, "", "")

    @Test
    fun getDataFromEventUseCaseWhenInitialised() = runTest {
        val viewModel: EventViewModel = mockSuccessFulDataViewModel()

        viewModel.events.first()

        verify(getEventsUseCase, times(1)).invoke()
    }

    @Test
    fun propagateDataWhenGetUseCaseReturnsSuccessful() = runTest {
        val viewModel = mockSuccessFulDataViewModel()

        assertEquals(VideoListSate.Data(listOf(eventItem)), viewModel.events.first())
    }

    @Test
    fun propagateErrorWhenGetUseCaseReturnsError() = runTest {
        val viewModel = mockErrorViewModel()

        assertEquals(VideoListSate.Error("Damn back-end :-D"), viewModel.events.first())
    }

    private suspend fun mockSuccessFulDataViewModel(): EventViewModel {
        whenever(getEventsUseCase()).thenReturn(
            flow {
                emit(Resource.Success(listOf(eventItem)))
            }
        )

        return EventViewModel(getEventsUseCase)
    }

    private suspend fun mockErrorViewModel(): EventViewModel {
        whenever(getEventsUseCase()).thenReturn(
            flow {
                emit(Resource.Error("Damn back-end :-D"))
            }
        )

        return EventViewModel(getEventsUseCase)
    }
}