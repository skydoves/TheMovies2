/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.themovies2.repository

import androidx.lifecycle.MutableLiveData
import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.client.MovieClient
import com.skydoves.themovies2.api.message
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject
constructor(
  private val movieClient: MovieClient,
  val movieDao: MovieDao
) : Repository {

  init {
    Timber.d("Injection MovieRepository")
  }

  suspend fun loadKeywordList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Keyword>>()
    movieClient.fetchKeywords(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.keywords = data.keywords
            liveData.postValue(data.keywords)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }

  suspend fun loadVideoList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Video>>()
    movieClient.fetchVideos(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.videos = data.results
            liveData.postValue(data.results)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }

  suspend fun loadReviewsList(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Review>>()
    movieClient.fetchReviews(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            val movie = movieDao.getMovie(id)
            movie.reviews = data.results
            liveData.postValue(data.results)
            movieDao.updateMovie(movie)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    liveData
  }
}
