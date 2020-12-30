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

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.room.TvDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class TvRepository constructor(
  private val tvService: TvService,
  private val tvDao: TvDao
) : Repository {

  init {
    Timber.d("Injection TvRepository")
  }

  @WorkerThread
  suspend fun loadKeywordList(id: Int) = flow {
    val tv = tvDao.getTv(id)
    var keywords = tv.keywords
    val response = tvService.fetchKeywords(id)
    if (keywords.isNullOrEmpty()) {
      response.suspendOnSuccess {
        data?.let { data ->
          keywords = data.keywords
          tv.keywords = keywords
          tvDao.updateTv(tv)
          emit(keywords)
        }
      }
    } else {
      emit(keywords)
    }
  }.flowOn(Dispatchers.IO)

  @WorkerThread
  suspend fun loadVideoList(id: Int) = flow {
    val tv = tvDao.getTv(id)
    var videos = tv.videos
    val response = tvService.fetchVideos(id)
    if (videos.isNullOrEmpty()) {
      response.suspendOnSuccess {
        data?.let { data ->
          videos = data.results
          tv.videos = videos
          tvDao.updateTv(tv)
          emit(videos)
        }
      }
    } else {
      emit(videos)
    }
  }.flowOn(Dispatchers.IO)

  @WorkerThread
  suspend fun loadReviewsList(id: Int) = flow {
    val tv = tvDao.getTv(id)
    var reviews = tv.reviews
    if (reviews.isNullOrEmpty()) {
      val response = tvService.fetchReviews(id)
      response.suspendOnSuccess {
        data?.let { data ->
          reviews = data.results
          tv.reviews = reviews
          tvDao.updateTv(tv)
          emit(reviews)
        }
      }
    } else {
      emit(reviews)
    }
  }.flowOn(Dispatchers.IO)
}
