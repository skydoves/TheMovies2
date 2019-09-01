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
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.observe
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityPersonDetailBinding
import com.skydoves.themovies2.extension.checkIsMaterialVersion
import com.skydoves.themovies2.models.entity.Person
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class PersonDetailActivity : ViewModelActivity() {

  private val vm by viewModel<PersonDetailViewModel>()
  private val binding by binding<ActivityPersonDetailBinding>(R.layout.activity_person_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    vm.postPersonId(getPersonFromIntent().id)
    with(binding) {
      lifecycleOwner = this@PersonDetailActivity
      viewModel = vm
      person = getPersonFromIntent()
    }
    initializeUI()
    observeMessages()
  }

  private fun initializeUI() {
    toolbar_home.setOnClickListener { onBackPressed() }
    toolbar_title.text = getPersonFromIntent().name
  }

  private fun getPersonFromIntent() =
    intent.getParcelableExtra(personId) as Person

  private fun observeMessages() =
    this.vm.toastLiveData.observe(this) { toast(it) }

  companion object {
    const val personId = "person"
    private const val intent_requestCode = 1000

    fun startActivity(activity: Activity?, person: Person, view: View) {
      if (activity != null) {
        if (checkIsMaterialVersion()) {
          val intent = Intent(activity, PersonDetailActivity::class.java)
          ViewCompat.getTransitionName(view)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
            intent.putExtra(personId, person)
            activity.startActivityForResult(intent, intent_requestCode, options.toBundle())
          }
        } else {
          activity.startActivity<PersonDetailActivity>(personId to person)
        }
      }
    }
  }
}
