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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityPersonDetailBinding
import com.skydoves.themovies2.extension.checkIsMaterialVersion
import com.skydoves.themovies2.models.entity.Person
import org.koin.android.viewmodel.ext.android.getViewModel

class PersonDetailActivity : ViewModelActivity() {

  private val binding: ActivityPersonDetailBinding by binding(R.layout.activity_person_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intentPerson = intent.getParcelableExtra<Person>(personId) as Person
    with(binding) {
      activity = this@PersonDetailActivity
      lifecycleOwner = this@PersonDetailActivity
      viewModel =
        getViewModel(PersonDetailViewModel::class).apply { postPersonId(intentPerson.id) }
      person = intentPerson
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    const val personId = "person"
    private const val intent_requestCode = 1000

    fun startActivity(context: Context, person: Person, view: View) {
      if (context is Activity) {
        if (checkIsMaterialVersion()) {
          val intent = Intent(context, PersonDetailActivity::class.java)
          ViewCompat.getTransitionName(view)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, view, it)
            intent.putExtra(personId, person)
            context.startActivityForResult(intent, intent_requestCode, options.toBundle())
          }
        } else {
          context.startActivity(
            Intent(context, PersonDetailActivity::class.java).putExtra(personId, person))
        }
      }
    }
  }
}
