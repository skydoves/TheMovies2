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
import com.skydoves.themovies2.api.ApiUtil.getCall
import com.skydoves.themovies2.api.client.TheDiscoverClient
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.models.network.DiscoverMovieResponse
import com.skydoves.themovies2.models.network.DiscoverTvResponse
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.room.TvDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DiscoveryRepositoryTest {

  private lateinit var repository: DiscoverRepository
  private lateinit var client: TheDiscoverClient
  private val service = mock<TheDiscoverService>()
  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    client = TheDiscoverClient(service)
    repository = DiscoverRepository(client, movieDao, tvDao)
  }

  @Test
  fun loadMovieListFromNetworkTest() = runBlocking {
    val liveData = MutableLiveData<List<Movie>>()
    val loadFromDB = movieDao.getMovieList(1)
    whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)
    liveData.postValue(loadFromDB)

    val mockResponse = DiscoverMovieResponse(1, emptyList(), 100, 10)
    whenever(service.fetchDiscoverMovie(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadMovies(1) { }
    verify(movieDao, times(2)).getMovieList(1)

    val observer = mock<Observer<List<Movie>>>()
    data.observeForever(observer)
    val updatedData: List<Movie> = arrayListOf()
    whenever(movieDao.getMovieList(1)).thenReturn(updatedData)
    liveData.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    client.fetchDiscoverMovie(1) {
      when (it) {
        is ApiResponse.Success -> assertEquals(it, `is`(mockResponse))
        else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadTvListFromNetworkTest() = runBlocking {
    val liveData = MutableLiveData<List<Tv>>()
    val loadFromDB = tvDao.getTvList(1)
    whenever(tvDao.getTvList(1)).thenReturn(loadFromDB)
    liveData.postValue(loadFromDB)

    val mockResponse = DiscoverTvResponse(1, emptyList(), 100, 10)
    whenever(service.fetchDiscoverTv(1)).thenReturn(getCall(mockResponse))

    val data = repository.loadTvs(1) { }
    verify(tvDao, times(2)).getTvList(1)

    val observer = mock<Observer<List<Tv>>>()
    data.observeForever(observer)
    val updatedData: List<Tv> = arrayListOf()
    whenever(tvDao.getTvList(1)).thenReturn(updatedData)
    liveData.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    client.fetchDiscoverTv(1) {
      when (it) {
        is ApiResponse.Success -> assertEquals(it, `is`(mockResponse))
        else -> assertThat(it, instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
