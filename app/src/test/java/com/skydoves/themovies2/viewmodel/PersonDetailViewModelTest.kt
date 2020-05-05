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

package com.skydoves.themovies2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.themovies2.MainCoroutinesRule
import com.skydoves.themovies2.api.ApiUtil
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.repository.PeopleRepository
import com.skydoves.themovies2.room.PeopleDao
import com.skydoves.themovies2.utils.MockTestUtil
import com.skydoves.themovies2.view.ui.details.person.PersonDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PersonDetailViewModelTest {

  private lateinit var viewModel: PersonDetailViewModel
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
    this.repository = PeopleRepository(service, peopleDao)
    this.viewModel = PersonDetailViewModel(repository)
  }

  @Test
  fun loadPersonDetailFromLocalDatabase() {
    coroutinesRule.runBlockingTest {
      val mockResponse = MockTestUtil.mockPersonDetail()
      whenever(service.fetchPersonDetail(1)).thenReturn(ApiUtil.getCall(mockResponse))
      whenever(peopleDao.getPerson(1)).thenReturn(MockTestUtil.mockPerson())

      val data = viewModel.personLiveData
      val observer = mock<Observer<PersonDetail>>()
      data.observeForever(observer)

      viewModel.postPersonId(1)
      viewModel.postPersonId(1)

      verify(peopleDao, atLeastOnce()).getPerson(1)
      verify(observer, atLeastOnce()).onChanged(mockResponse)
      data.removeObserver(observer)
    }
  }
}
