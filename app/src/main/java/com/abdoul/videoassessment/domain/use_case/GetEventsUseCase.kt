package com.abdoul.videoassessment.domain.use_case

import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.model.EventItem
import com.abdoul.videoassessment.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(private val repository: VideoRepository) {

    operator fun invoke(): Flow<Resource<List<EventItem>>> = flow {
        try {
            emit(Resource.Loading())
            val events = repository.getEvents().sortedBy { it.date }
            emit(Resource.Success(events))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("Error, please ensure you have network connection"))
        }
    }
}