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
import com.skydoves.themovies2.api.client.TheDiscoverClient
import com.skydoves.themovies2.api.message
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.room.TvDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DiscoverRepository constructor(
  private val discoverClient: TheDiscoverClient,
  private val movieDao: MovieDao,
  private val tvDao: TvDao
) : Repository {

  init {
    Timber.d("Injection DiscoverRepository")
  }

  suspend fun loadMovies(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveDate = MutableLiveData<List<Movie>>()
    var movies = movieDao.getMovieList(page)
    if (movies.isEmpty()) {
      discoverClient.fetchDiscoverMovie(page) { response ->
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              movies = data.results
              movies.forEach { it.page = page }
              liveDate.postValue(movies)
              movieDao.insertMovieList(movies)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveDate.postValue(movies)
    liveDate
  }

  suspend fun loadTvs(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveDate = MutableLiveData<List<Tv>>()
    var tvs = tvDao.getTvList(page)
    if (tvs.isEmpty()) {
      discoverClient.fetchDiscoverTv(page) { response ->
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              tvs = data.results
              tvs.forEach { it.page = page }
              liveDate.postValue(tvs)
              tvDao.insertTv(tvs)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    liveDate.postValue(tvs)
    liveDate
  }
}
