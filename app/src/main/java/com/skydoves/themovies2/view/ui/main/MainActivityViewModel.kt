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
package com.skydoves.themovies2.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.repository.DiscoverRepository
import com.skydoves.themovies2.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

class MainActivityViewModel @Inject
constructor(
  private val discoverRepository: DiscoverRepository,
  private val peopleRepository: PeopleRepository
) : ViewModel() {

  private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val movieListLiveData: LiveData<List<Movie>>

  private var tvPageLiveData: MutableLiveData<Int> = MutableLiveData()
  val tvListLiveData: LiveData<List<Tv>>

  private var peoplePageLiveData: MutableLiveData<Int> = MutableLiveData()
  val peopleLiveData: LiveData<List<Person>>

  val toastLiveData: MutableLiveData<String> = MutableLiveData()

  init {
    Timber.d("injection MainActivityViewModel")

    movieListLiveData = moviePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        discoverRepository.loadMovies(page)
        { toastLiveData.postValue(it) }
      }
    }

    tvListLiveData = tvPageLiveData.switchMap { page ->
      launchOnViewModelScope {
        discoverRepository.loadTvs(page)
        { toastLiveData.postValue(it) }
      }
    }

    peopleLiveData = peoplePageLiveData.switchMap { page ->
      launchOnViewModelScope {
        peopleRepository.loadPeople(page)
        { toastLiveData.postValue(it) }
      }
    }
  }

  private fun <T> launchOnViewModelScope(block: suspend () -> T): LiveData<T> {
    return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
      try {
        emit(block())
      } catch (e: Throwable) {
        Timber.e(e)
      }
    }
  }

  fun getMovieListValues() = movieListLiveData.value
  fun postMoviePage(page: Int) = moviePageLiveData.postValue(page)

  fun getTvListValues() = tvListLiveData.value
  fun postTvPage(page: Int) = tvPageLiveData.postValue(page)

  fun getPeopleValues() = peopleLiveData.value
  fun postPeoplePage(page: Int) = peoplePageLiveData.postValue(page)
}
