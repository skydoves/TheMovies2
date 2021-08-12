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
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
  private val movieRepository: MovieRepository
) : BindingViewModel() {

  private val movieIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

  private val keywordListFlow = movieIdSharedFlow.flatMapLatest {
    movieRepository.loadKeywordList(it)
  }

  @get:Bindable
  val keywordList: List<Keyword>? by keywordListFlow.asBindingProperty(viewModelScope, null)

  private val videoListFlow = movieIdSharedFlow.flatMapLatest {
    movieRepository.loadVideoList(it)
  }

  @get:Bindable
  val videoList: List<Video>? by videoListFlow.asBindingProperty(viewModelScope, null)

  private val reviewListFlow = movieIdSharedFlow.flatMapLatest {
    movieRepository.loadReviewsList(it)
  }

  @get:Bindable
  val reviewList: List<Review>? by reviewListFlow.asBindingProperty(viewModelScope, null)

  init {
    Timber.d("Injection MovieDetailViewModel")
  }

  @MainThread
  fun getMovieListFromId(id: Int) = movieIdSharedFlow.tryEmit(id)
}
