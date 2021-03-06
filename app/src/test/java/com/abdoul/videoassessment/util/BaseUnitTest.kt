package com.abdoul.videoassessment.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdoul.videoassessment.util.MainCoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseUnitTest {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule =
        InstantTaskExecutorRule() //Allows execution of LiveData to happen instantly
}