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

package com.skydoves.themovies2.view.ui.details.movie

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.skydoves.themovies2.base.DispatchViewModel
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class MovieDetailViewModel constructor(
  private val movieRepository: MovieRepository
) : DispatchViewModel() {

  private val movieIdStateFlow: MutableStateFlow<Int> = MutableStateFlow(0)

  val keywordListLiveData: LiveData<List<Keyword>?>
  val videoListLiveData: LiveData<List<Video>?>
  val reviewListLiveData: LiveData<List<Review>?>

  init {
    Timber.d("Injection MovieDetailViewModel")

    this.keywordListLiveData = movieIdStateFlow.asLiveData().switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadKeywordList(id).asLiveData()
      }
    }

    this.videoListLiveData = movieIdStateFlow.asLiveData().switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadVideoList(id).asLiveData()
      }
    }

    this.reviewListLiveData = movieIdStateFlow.asLiveData().switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadReviewsList(id).asLiveData()
      }
    }
  }

  @MainThread
  fun getMovieListFromId(id: Int) {
    movieIdStateFlow.value = id
  }
}
