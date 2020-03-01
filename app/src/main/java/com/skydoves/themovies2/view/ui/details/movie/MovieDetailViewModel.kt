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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.skydoves.themovies2.compose.DispatchViewModel
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.repository.MovieRepository
import timber.log.Timber

class MovieDetailViewModel constructor(
  private val movieRepository: MovieRepository
) : DispatchViewModel() {

  private val movieIdLiveData: MutableLiveData<Int> = MutableLiveData()
  val keywordListLiveData: LiveData<List<Keyword>>
  val videoListLiveData: LiveData<List<Video>>
  val reviewListLiveData: LiveData<List<Review>>
  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("Injection MovieDetailViewModel")

    this.keywordListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadKeywordList(id) { toastLiveData.postValue(it) }
      }
    }

    this.videoListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadVideoList(id) { toastLiveData.postValue(it) }
      }
    }

    this.reviewListLiveData = movieIdLiveData.switchMap { id ->
      launchOnViewModelScope {
        movieRepository.loadReviewsList(id) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postMovieId(id: Int) = movieIdLiveData.postValue(id)
}
