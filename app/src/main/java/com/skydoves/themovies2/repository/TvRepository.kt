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

import androidx.lifecycle.MutableLiveData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import com.skydoves.sandwich.request
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.room.TvDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class TvRepository constructor(
  private val tvService: TvService,
  private val tvDao: TvDao
) : Repository {

  init {
    Timber.d("Injection TvRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    val tv = tvDao.getTv(id)
    var keywords = tv.keywords
    tvService.fetchKeywords(id).request { response ->
      response.onSuccess {
        data?.let { data ->
          keywords = data.keywords
          tv.keywords = keywords
          liveData.postValue(keywords)
          tvDao.updateTv(tv)
        }
      }.onError {
        error(message())
      }.onException {
        error(message())
      }
    }
    liveData.apply { postValue(keywords) }
  }

  suspend fun loadVideoList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Video>>()
    val tv = tvDao.getTv(id)
    var videos = tv.videos
    tvService.fetchVideos(id).request { response ->
      response.onSuccess {
        data?.let { data ->
          videos = data.results
          tv.videos = videos
          liveData.postValue(videos)
          tvDao.updateTv(tv)
        }
      }.onError {
        error(message())
      }.onException {
        error(message())
      }
    }
    liveData.apply { postValue(videos) }
  }

  suspend fun loadReviewsList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Review>>()
    val tv = tvDao.getTv(id)
    var reviews = tv.reviews
    if (reviews.isNullOrEmpty()) {
      tvService.fetchReviews(id).request { response ->
        response.onSuccess {
          data?.let { data ->
            reviews = data.results
            tv.reviews = reviews
            liveData.postValue(reviews)
            tvDao.updateTv(tv)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveData.apply { postValue(reviews) }
  }
}
