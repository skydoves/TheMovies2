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
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.room.TvDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DiscoverRepository constructor(
  private val discoverService: TheDiscoverService,
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
      discoverService.fetchDiscoverMovie(page).request { response ->
        response.onSuccess {
          data?.let { data ->
            movies = data.results
            movies.forEach { it.page = page }
            liveDate.postValue(movies)
            movieDao.insertMovieList(movies)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveDate.apply { postValue(movies) }
  }

  suspend fun loadTvs(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveDate = MutableLiveData<List<Tv>>()
    var tvs = tvDao.getTvList(page)
    if (tvs.isEmpty()) {
      discoverService.fetchDiscoverTv(page).request { response ->
        response.onSuccess {
          data?.let { data ->
            tvs = data.results
            tvs.forEach { it.page = page }
            liveDate.postValue(tvs)
            tvDao.insertTv(tvs)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveDate.apply { postValue(tvs) }
  }
}
