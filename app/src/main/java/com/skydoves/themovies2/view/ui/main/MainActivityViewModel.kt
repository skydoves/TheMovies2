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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.skydoves.themovies2.compose.DispatchViewModel
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.repository.DiscoverRepository
import com.skydoves.themovies2.repository.PeopleRepository
import timber.log.Timber

class MainActivityViewModel constructor(
  private val discoverRepository: DiscoverRepository,
  private val peopleRepository: PeopleRepository
) : DispatchViewModel() {

  private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val movieListLiveData: LiveData<List<Movie>>

  private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
  val tvListLiveData: LiveData<List<Tv>>

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val peopleLiveData: LiveData<List<Person>>

  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("injection MainActivityViewModel")

    this.movieListLiveData = moviePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        discoverRepository.loadMovies(page) { toastLiveData.postValue(it) }
      }
    }

    this.tvListLiveData = tvPageLiveData.switchMap { page ->
      launchOnViewModelScope {
        discoverRepository.loadTvs(page) { toastLiveData.postValue(it) }
      }
    }

    this.peopleLiveData = peoplePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        peopleRepository.loadPeople(page) { toastLiveData.postValue(it) }
      }
    }
  }

  fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

  fun postTvPage(page: Int) = tvPageLiveData.postValue(page)

  fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}
