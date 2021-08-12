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

package com.skydoves.themovies2.view.ui.details.person

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
  private val peopleRepository: PeopleRepository
) : BindingViewModel() {

  @get:Bindable
  var isLoading: Boolean by bindingProperty(false)
    private set

  private val personIdSharedFlow: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

  private val personFlow: Flow<PersonDetail?> = personIdSharedFlow.flatMapLatest {
    isLoading = true
    peopleRepository.loadPersonDetail(it) {
      isLoading = false
    }
  }

  @get:Bindable
  val person: PersonDetail? by personFlow.asBindingProperty(viewModelScope, null)

  init {
    Timber.d("Injection : PersonDetailViewModel")
  }

  fun postPersonId(id: Int) = personIdSharedFlow.tryEmit(id)
}
