package com.abdoul.videoassessment.di

import android.content.Context
import com.abdoul.videoassessment.common.Constants
import com.abdoul.videoassessment.data.remote.VideoApi
import com.abdoul.videoassessment.data.repository.VideoRepositoryImpl
import com.abdoul.videoassessment.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient(@ApplicationContext context: Context): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024L
        val cacheDirectory = File(context.cacheDir, "video_cache")
        val cache = Cache(cacheDirectory, cacheSize)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoApi(okHttpClient: OkHttpClient): VideoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(VideoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(api: VideoApi): VideoRepository {
        return VideoRepositoryImpl(api)
    }
}