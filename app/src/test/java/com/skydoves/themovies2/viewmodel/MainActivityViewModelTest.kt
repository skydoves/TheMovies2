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

package com.skydoves.themovies2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies2.MainCoroutinesRule
import com.skydoves.themovies2.api.ApiUtil
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.models.network.DiscoverMovieResponse
import com.skydoves.themovies2.models.network.DiscoverTvResponse
import com.skydoves.themovies2.models.network.PeopleResponse
import com.skydoves.themovies2.repository.DiscoverRepository
import com.skydoves.themovies2.repository.PeopleRepository
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.room.PeopleDao
import com.skydoves.themovies2.room.TvDao
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockMovieList
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPersonList
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockTvList
import com.skydoves.themovies2.view.ui.main.MainActivityViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

  private lateinit var viewModel: MainActivityViewModel

  private lateinit var discoverRepository: DiscoverRepository
  private val discoverService = mock<TheDiscoverService>()
  private val movieDao = mock<MovieDao>()
  private val tvDao = mock<TvDao>()

  private lateinit var peopleRepository: PeopleRepository
  private val peopleService = mock<PeopleService>()
  private val peopleDao = mock<PeopleDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    this.discoverRepository = DiscoverRepository(discoverService, movieDao, tvDao)
    this.peopleRepository = PeopleRepository(peopleService, peopleDao)
    this.viewModel = MainActivityViewModel(discoverRepository, peopleRepository)
  }

  @Test
  fun loadMovieListFromNetwork() = runBlocking {
    val loadFromDB = emptyList<Movie>()
    whenever(movieDao.getMovieList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverMovieResponse(1, mockMovieList(), 100, 10)
    whenever(discoverService.fetchDiscoverMovie(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = viewModel.movieListLiveData
    val observer = mock<Observer<List<Movie>>>()
    data.observeForever(observer)

    viewModel.postMoviePage(1)
    viewModel.postMoviePage(1)

    verify(movieDao, atLeastOnce()).getMovieList(1)
    verify(discoverService, atLeastOnce()).fetchDiscoverMovie(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB)
    data.removeObserver(observer)
  }

  @Test
  fun loadTvListFromNetwork() = runBlocking {
    val loadFromDB = emptyList<Tv>()
    whenever(tvDao.getTvList(1)).thenReturn(loadFromDB)

    val mockResponse = DiscoverTvResponse(1, mockTvList(), 100, 10)
    whenever(discoverService.fetchDiscoverTv(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = viewModel.tvListLiveData
    val observer = mock<Observer<List<Tv>>>()
    data.observeForever(observer)

    viewModel.postTvPage(1)
    viewModel.postTvPage(1)

    verify(tvDao, atLeastOnce()).getTvList(1)
    verify(discoverService, atLeastOnce()).fetchDiscoverTv(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB)
    data.removeObserver(observer)
  }

  @Test
  fun loadPeopleFromNetwork() = runBlocking {
    val loadFromDB = emptyList<Person>()
    whenever(peopleDao.getPeople(1)).thenReturn(loadFromDB)

    val mockResponse = PeopleResponse(1, mockPersonList(), 100, 10)
    whenever(peopleService.fetchPopularPeople(1)).thenReturn(ApiUtil.getCall(mockResponse))

    val data = viewModel.peopleLiveData
    val observer = mock<Observer<List<Person>>>()
    data.observeForever(observer)

    viewModel.postPeoplePage(1)
    viewModel.postPeoplePage(1)

    verify(peopleDao, atLeastOnce()).getPeople(1)
    verify(peopleService, atLeastOnce()).fetchPopularPeople(1)
    verify(observer, atLeastOnce()).onChanged(loadFromDB)
    data.removeObserver(observer)
  }
}
