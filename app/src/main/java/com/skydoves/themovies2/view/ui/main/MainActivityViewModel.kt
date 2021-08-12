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

package com.skydoves.themovies2.view.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.repository.DiscoverRepository
import com.skydoves.themovies2.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
  private val discoverRepository: DiscoverRepository,
  private val peopleRepository: PeopleRepository
) : BindingViewModel() {

  @get:Bindable
  var isMovieListLoading: Boolean by bindingProperty(false)
    private set

  @get:Bindable
  var isTvListLoading: Boolean by bindingProperty(false)
    private set

  @get:Bindable
  var isPeopleListLoading: Boolean by bindingProperty(false)
    private set

  private val moviePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
  private val movieListFlow = moviePageStateFlow.flatMapLatest {
    isMovieListLoading = true
    discoverRepository.loadMovies(it) {
      isMovieListLoading = false
    }
  }

  @get:Bindable
  val movieList: List<Movie> by movieListFlow.asBindingProperty(viewModelScope, emptyList())

  private val tvPageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
  private val tvListFlow = tvPageStateFlow.flatMapLatest {
    isTvListLoading = true
    discoverRepository.loadTvs(it) {
      isTvListLoading = false
    }
  }

  @get:Bindable
  val tvList: List<Tv> by tvListFlow.asBindingProperty(viewModelScope, emptyList())

  private val peoplePageStateFlow: MutableStateFlow<Int> = MutableStateFlow(1)
  private val peopleListFlow = peoplePageStateFlow.flatMapLatest {
    isPeopleListLoading = true
    peopleRepository.loadPeople(it) {
      isPeopleListLoading = false
    }
  }

  @get:Bindable
  val peopleList: List<Person> by peopleListFlow.asBindingProperty(viewModelScope, emptyList())

  init {
    Timber.d("injection MainActivityViewModel")
  }

  fun postMoviePage(page: Int) = moviePageStateFlow.tryEmit(page)

  fun postTvPage(page: Int) = tvPageStateFlow.tryEmit(page)

  fun postPeoplePage(page: Int) = peoplePageStateFlow.tryEmit(page)
}
