package com.abdoul.videoassessment.domain.use_case

import com.abdoul.videoassessment.common.Resource
import com.abdoul.videoassessment.domain.model.ScheduleItem
import com.abdoul.videoassessment.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(private val repository: VideoRepository) {

    operator fun invoke(): Flow<Resource<List<ScheduleItem>>> = flow {
        try {
            emit(Resource.Loading())
            val events = repository.getSchedule().sortedBy { it.date }
            emit(Resource.Success(events))
        } catch (ex: HttpException) {
            emit(Resource.Error(ex.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error("Error, please ensure you have network connection"))
        }
    }
}