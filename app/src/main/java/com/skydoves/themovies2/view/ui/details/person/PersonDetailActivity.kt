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

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.themovies2.R
import com.skydoves.themovies2.databinding.ActivityPersonDetailBinding
import com.skydoves.themovies2.models.entity.Person
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonDetailActivity :
  BindingActivity<ActivityPersonDetailBinding>(R.layout.activity_person_detail) {

  private val vm: PersonDetailViewModel by viewModels()
  private val intentPerson: Person by bundleNonNull(PERSON_ID)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding {
      activity = this@PersonDetailActivity
      viewModel = vm.apply { postPersonId(intentPerson.id) }
      person = intentPerson
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    const val PERSON_ID = "person"

    fun startActivity(context: Context, person: Person?) {
      context.intentOf<PersonDetailActivity> {
        putExtra(PERSON_ID to person)
        startActivity(context)
      }
    }
  }
}
