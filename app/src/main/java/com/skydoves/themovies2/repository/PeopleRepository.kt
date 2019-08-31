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

import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.client.PeopleClient
import com.skydoves.themovies2.api.message
import com.skydoves.themovies2.room.PeopleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject
constructor(
  private val peopleClient: PeopleClient,
  private val peopleDao: PeopleDao
) : Repository {

  init {
    Timber.d("Injection PeopleRepository")
  }

  suspend fun loadPeople(page: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    var people = peopleDao.getPeople(page)
    if (people.isEmpty()) {
      peopleClient.fetchPopularPeople(page) { response ->
        when (response) {
          is ApiResponse.Success -> {
            response.data?.let { data ->
              people = data.results
              people.forEach { it.page = page }
              peopleDao.insertPeople(people)
            }
          }
          is ApiResponse.Failure.Error -> error(response.message())
          is ApiResponse.Failure.Exception -> error(response.message())
        }
      }
    }
    people
  }

  suspend fun loadPersonDetail(id: Int, error: (String) -> Unit) = withContext(Dispatchers.IO) {
    val person = peopleDao.getPerson(id)
    peopleClient.fetchPersonDetai(id) { response ->
      when (response) {
        is ApiResponse.Success -> {
          response.data?.let { data ->
            person.personDetail = data
            peopleDao.updatePerson(person)
          }
        }
        is ApiResponse.Failure.Error -> error(response.message())
        is ApiResponse.Failure.Exception -> error(response.message())
      }
    }
    person.personDetail
  }
}
