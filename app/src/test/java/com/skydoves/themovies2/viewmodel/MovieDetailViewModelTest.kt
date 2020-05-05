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
import com.skydoves.themovies2.api.service.MovieService
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.network.KeywordListResponse
import com.skydoves.themovies2.models.network.ReviewListResponse
import com.skydoves.themovies2.models.network.VideoListResponse
import com.skydoves.themovies2.repository.MovieRepository
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockKeywordList
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockMovie
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockReviewList
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockVideoList
import com.skydoves.themovies2.view.ui.details.movie.MovieDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

  private lateinit var viewModel: MovieDetailViewModel
  private lateinit var repository: MovieRepository
  private val service = mock<MovieService>()
  private val movieDao = mock<MovieDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    this.repository = MovieRepository(service, movieDao)
    this.viewModel = MovieDetailViewModel(repository)
  }

  @Test
  fun loadKeywordListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = mockMovie()
      whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

      val mockResponse = KeywordListResponse(1, mockKeywordList())
      whenever(service.fetchKeywords(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.keywordListLiveData
      val observer = mock<Observer<List<Keyword>>>()
      data.observeForever(observer)

      viewModel.postMovieId(1)
      viewModel.postMovieId(1)

      verify(movieDao, atLeastOnce()).getMovie(1)
      verify(service, atLeastOnce()).fetchKeywords(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.keywords)
      data.removeObserver(observer)
    }
  }

  @Test
  fun loadVideoListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = mockMovie()
      whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

      val mockResponse = VideoListResponse(1, mockVideoList())
      whenever(service.fetchVideos(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.videoListLiveData
      val observer = mock<Observer<List<Video>>>()
      data.observeForever(observer)

      viewModel.postMovieId(1)
      viewModel.postMovieId(1)

      verify(movieDao, atLeastOnce()).getMovie(1)
      verify(service, atLeastOnce()).fetchVideos(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.videos)
      data.removeObserver(observer)
    }
  }

  @Test
  fun loadReviewsListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = mockMovie()
      whenever(movieDao.getMovie(1)).thenReturn(loadFromDB)

      val mockResponse = ReviewListResponse(1, 0, mockReviewList(), 0, 0)
      whenever(service.fetchReviews(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.reviewListLiveData
      val observer = mock<Observer<List<Review>>>()
      data.observeForever(observer)

      viewModel.postMovieId(1)
      viewModel.postMovieId(1)

      verify(movieDao, atLeastOnce()).getMovie(1)
      verify(service, atLeastOnce()).fetchReviews(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.reviews)
      data.removeObserver(observer)
    }
  }
}
