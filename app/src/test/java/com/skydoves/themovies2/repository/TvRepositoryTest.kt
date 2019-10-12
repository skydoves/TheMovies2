/*
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.themovies2.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies2.MainCoroutinesRule
import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.ApiUtil
import com.skydoves.themovies2.api.client.TvClient
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.network.KeywordListResponse
import com.skydoves.themovies2.models.network.ReviewListResponse
import com.skydoves.themovies2.models.network.VideoListResponse
import com.skydoves.themovies2.room.TvDao
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockTv
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvRepositoryTest {

  private lateinit var repository: TvRepository
  private lateinit var client: TvClient
  private val service = mock<TvService>()
  private val tvDao = mock<TvDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    client = TvClient(service)
    repository = TvRepository(client, tvDao)
  }

  @Test
  fun loadKeywordListFromNetworkTest() = runBlocking {
    val liveData = MutableLiveData<List<Keyword>>()
    whenever(tvDao.getTv(1)).thenReturn(mockTv())

    val loadFromDB = tvDao.getTv(1)
    liveData.postValue(loadFromDB.keywords)

    val mockResponse = KeywordListResponse(1, emptyList())
    whenever(service.fetchKeywords(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = repository.loadKeywordList(1) { }
    verify(tvDao, times(2)).getTv(1)

    val observer = mock<Observer<List<Keyword>>>()
    data.observeForever(observer)
    val updatedData = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(updatedData)
    liveData.postValue(updatedData.keywords)
    verify(observer).onChanged(updatedData.keywords)

    client.fetchKeywords(1) {
      when (it) {
        is ApiResponse.Success -> TestCase.assertEquals(it, CoreMatchers.`is`(mockResponse))
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadVideoListFromNetworkTest() = runBlocking {
    val liveData = MutableLiveData<List<Video>>()
    whenever(tvDao.getTv(1)).thenReturn(mockTv())

    val loadFromDB = tvDao.getTv(1)
    liveData.postValue(loadFromDB.videos)

    val mockResponse = VideoListResponse(1, emptyList())
    whenever(service.fetchVideos(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = repository.loadVideoList(1) { }
    verify(tvDao, times(2)).getTv(1)

    val observer = mock<Observer<List<Video>>>()
    data.observeForever(observer)
    val updatedData = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(updatedData)
    liveData.postValue(updatedData.videos)
    verify(observer).onChanged(updatedData.videos)

    client.fetchVideos(1) {
      when (it) {
        is ApiResponse.Success -> TestCase.assertEquals(it, CoreMatchers.`is`(mockResponse))
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadReviewListFromNetworkTest() = runBlocking {
    val liveData = MutableLiveData<List<Review>>()
    whenever(tvDao.getTv(1)).thenReturn(mockTv())

    val loadFromDB = tvDao.getTv(1)
    liveData.postValue(loadFromDB.reviews)

    val mockResponse = ReviewListResponse(1, 0, emptyList(), 0, 0)
    whenever(service.fetchReviews(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = repository.loadReviewsList(1) { }
    verify(tvDao, times(2)).getTv(1)

    val observer = mock<Observer<List<Review>>>()
    data.observeForever(observer)
    val updatedData = mockTv()
    whenever(tvDao.getTv(1)).thenReturn(updatedData)
    liveData.postValue(updatedData.reviews)
    verify(observer).onChanged(updatedData.reviews)

    client.fetchReviews(1) {
      when (it) {
        is ApiResponse.Success -> TestCase.assertEquals(it, CoreMatchers.`is`(mockResponse))
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}