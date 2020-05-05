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
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.room.PeopleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PeopleRepository constructor(
  private val peopleService: PeopleService,
  private val peopleDao: PeopleDao
) : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  suspend fun loadPeople(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<List<Person>>()
    var people = peopleDao.getPeople(page)
    if (people.isEmpty()) {
      peopleService.fetchPopularPeople(page).request { response ->
        response.onSuccess {
          data?.let { data ->
            people = data.results
            people.forEach { it.page = page }
            liveData.postValue(people)
            peopleDao.insertPeople(people)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveData.apply { postValue(people) }
  }

  suspend fun loadPersonDetail(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val liveData = MutableLiveData<PersonDetail>()
    val person = peopleDao.getPerson(id)
    var personDetail = person.personDetail
    if (personDetail == null) {
      peopleService.fetchPersonDetail(id).request { response ->
        response.onSuccess {
          data?.let { data ->
            personDetail = data
            person.personDetail = personDetail
            liveData.postValue(personDetail)
            peopleDao.updatePerson(person)
          }
        }.onError {
          error(message())
        }.onException {
          error(message())
        }
      }
    }
    liveData.apply { postValue(person.personDetail) }
  }
}
