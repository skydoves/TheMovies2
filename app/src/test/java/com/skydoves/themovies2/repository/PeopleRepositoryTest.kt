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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import com.skydoves.themovies2.MainCoroutinesRule
import com.skydoves.themovies2.api.ApiUtil.getCall
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.network.PeopleResponse
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.room.PeopleDao
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPersonDetail
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPersonList
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalStdlibApi
class PeopleRepositoryTest {

  private lateinit var repository: PeopleRepository
  private val service = mock<PeopleService>()
  private val peopleDao = mock<PeopleDao>()

  @ExperimentalCoroutinesApi
  @get:Rule
  var coroutinesRule = MainCoroutinesRule()

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @ExperimentalCoroutinesApi
  @Before
  fun setup() {
    repository = PeopleRepository(service, peopleDao)
  }

  @Test
  fun loadPeopleListFromNetworkTest() = runBlocking {
    val mockResponse = PeopleResponse(1, emptyList(), 0, 0)
    whenever(service.fetchPopularPeople(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPeople(1)).thenReturn(emptyList())

    val data = repository.loadPeople(1) { }
    val observer = mock<Observer<List<Person>>>()
    data.observeForever(observer)
    verify(peopleDao).getPeople(1)

    val loadFromDB = peopleDao.getPeople(1)
    data.postValue(loadFromDB)
    verify(observer, times(2)).onChanged(loadFromDB)

    val updatedData = mockPersonList()
    whenever(peopleDao.getPeople(1)).thenReturn(updatedData)
    data.postValue(updatedData)
    verify(observer).onChanged(updatedData)

    service.fetchPopularPeople(1).request {
      when (it) {
        is ApiResponse.Success -> {
          TestCase.assertEquals(it.data, CoreMatchers.`is`(mockResponse))
          TestCase.assertEquals(it.data?.results, CoreMatchers.`is`(updatedData))
        }
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }

  @Test
  fun loadPersonDetailFromNetworkTest() = runBlocking {
    val mockResponse = mockPersonDetail()
    whenever(service.fetchPersonDetail(1)).thenReturn(getCall(mockResponse))
    whenever(peopleDao.getPerson(1)).thenReturn(mockPerson())

    val data = repository.loadPersonDetail(1) { }
    val observer = mock<Observer<PersonDetail>>()
    data.observeForever(observer)
    verify(peopleDao).getPerson(1)

    val loadFromDB = peopleDao.getPerson(1)
    data.postValue(loadFromDB.personDetail)
    verify(observer, times(2)).onChanged(loadFromDB.personDetail)

    val updatedData = mockPerson()
    whenever(peopleDao.getPerson(1)).thenReturn(updatedData)
    data.postValue(updatedData.personDetail)
    verify(observer, times(3)).onChanged(updatedData.personDetail)

    service.fetchPersonDetail(1).request {
      when (it) {
        is ApiResponse.Success -> {
          TestCase.assertEquals(it, CoreMatchers.`is`(mockResponse))
          TestCase.assertEquals(it.data, CoreMatchers.`is`(updatedData.personDetail))
        }
        else -> MatcherAssert.assertThat(it, CoreMatchers.instanceOf(ApiResponse.Failure::class.java))
      }
    }
  }
}
