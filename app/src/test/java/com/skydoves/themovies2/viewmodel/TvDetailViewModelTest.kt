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
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.network.KeywordListResponse
import com.skydoves.themovies2.models.network.ReviewListResponse
import com.skydoves.themovies2.models.network.VideoListResponse
import com.skydoves.themovies2.repository.TvRepository
import com.skydoves.themovies2.room.TvDao
import com.skydoves.themovies2.utils.MockTestUtil
import com.skydoves.themovies2.view.ui.details.tv.TvDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvDetailViewModelTest {

  private lateinit var viewModel: TvDetailViewModel
  private lateinit var repository: TvRepository
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
    this.repository = TvRepository(service, tvDao)
    this.viewModel = TvDetailViewModel(repository)
  }

  @Test
  fun loadKeywordListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = MockTestUtil.mockTv()
      whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

      val mockResponse = KeywordListResponse(1, MockTestUtil.mockKeywordList())
      whenever(service.fetchKeywords(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.keywordListLiveData
      val observer = mock<Observer<List<Keyword>>>()
      data.observeForever(observer)

      viewModel.postTvId(1)
      viewModel.postTvId(1)

      verify(tvDao, atLeastOnce()).getTv(1)
      verify(service, atLeastOnce()).fetchKeywords(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.keywords)
      data.removeObserver(observer)
    }
  }

  @Test
  fun loadVideoListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = MockTestUtil.mockTv()
      whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

      val mockResponse = VideoListResponse(1, MockTestUtil.mockVideoList())
      whenever(service.fetchVideos(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.videoListLiveData
      val observer = mock<Observer<List<Video>>>()
      data.observeForever(observer)

      viewModel.postTvId(1)
      viewModel.postTvId(1)

      verify(tvDao, atLeastOnce()).getTv(1)
      verify(service, atLeastOnce()).fetchVideos(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.videos)
      data.removeObserver(observer)
    }
  }

  @Test
  fun loadReviewsListFromNetwork() {
    coroutinesRule.runBlockingTest {
      val loadFromDB = MockTestUtil.mockTv()
      whenever(tvDao.getTv(1)).thenReturn(loadFromDB)

      val mockResponse = ReviewListResponse(1, 0, MockTestUtil.mockReviewList(), 0, 0)
      whenever(service.fetchReviews(1)).thenReturn(ApiUtil.getCall(mockResponse))

      val data = viewModel.reviewListLiveData
      val observer = mock<Observer<List<Review>>>()
      data.observeForever(observer)

      viewModel.postTvId(1)
      viewModel.postTvId(1)

      verify(tvDao, atLeastOnce()).getTv(1)
      verify(service, atLeastOnce()).fetchReviews(1)
      verify(observer, atLeastOnce()).onChanged(loadFromDB.reviews)
      data.removeObserver(observer)
    }
  }
}
