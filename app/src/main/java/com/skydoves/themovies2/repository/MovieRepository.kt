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
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.themovies2.api.service.MovieService
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepository constructor(
  private val movieService: MovieService,
  private val movieDao: MovieDao
) : Repository {

  init {
    Timber.d("Injection MovieRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    val movie = movieDao.getMovie(id)
    var keywords = movie.keywords
    if (keywords.isNullOrEmpty()) {
      val response = movieService.fetchKeywords(id)
      response.suspendOnSuccess {
        data?.let { data ->
          keywords = data.keywords
          movie.keywords = keywords
          liveData.postValue(keywords)
          movieDao.updateMovie(movie)
        }
      }.suspendOnError {
        error(message())
      }.suspendOnException {
        error(message())
      }
    }
    liveData.apply { postValue(keywords) }
  }

  suspend fun loadVideoList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Video>>()
    val movie = movieDao.getMovie(id)
    var videos = movie.videos
    if (videos.isNullOrEmpty()) {
      val response = movieService.fetchVideos(id)
        .suspendOnSuccess {
          data?.let { data ->
            videos = data.results
            movie.videos = videos
            liveData.postValue(videos)
            movieDao.updateMovie(movie)
          }
        }.suspendOnError {
          error(message())
        }.suspendOnException {
          error(message())
        }
    }
    liveData.apply { postValue(videos) }
  }

  suspend fun loadReviewsList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Review>>()
    val movie = movieDao.getMovie(id)
    var reviews = movie.reviews
    if (reviews.isNullOrEmpty()) {
      val response = movieService.fetchReviews(id)
        .suspendOnSuccess {
          data?.let { data ->
            reviews = data.results
            movie.reviews = reviews
            liveData.postValue(reviews)
            movieDao.updateMovie(movie)
          }
        }.suspendOnError {
          error(message())
        }.suspendOnException {
          error(message())
        }
    }
    liveData.apply { postValue(reviews) }
  }
}
