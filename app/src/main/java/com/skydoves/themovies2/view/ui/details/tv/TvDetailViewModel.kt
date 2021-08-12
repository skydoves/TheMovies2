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

package com.skydoves.themovies2.view.ui.details.tv

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvDetailViewModel @Inject constructor(
  private val tvRepository: TvRepository
) : BindingViewModel() {

  private val tvIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

  private val keywordListFlow = tvIdSharedFlow.flatMapLatest {
    tvRepository.loadKeywordList(it)
  }

  @get:Bindable
  val keywordList: List<Keyword>? by keywordListFlow.asBindingProperty(viewModelScope, null)

  private val videoListFlow = tvIdSharedFlow.flatMapLatest {
    tvRepository.loadVideoList(it)
  }

  @get:Bindable
  val videoList: List<Video>? by videoListFlow.asBindingProperty(viewModelScope, null)

  private val reviewListFlow = tvIdSharedFlow.flatMapLatest {
    tvRepository.loadReviewsList(it)
  }

  @get:Bindable
  val reviewList: List<Review>? by reviewListFlow.asBindingProperty(viewModelScope, null)

  init {
    Timber.d("Injection TvDetailViewModel")
  }

  fun postTvId(id: Int) = tvIdSharedFlow.tryEmit(id)
}
