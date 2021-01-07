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

package com.skydoves.themovies2.binding

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.skydoves.themovies2.R
import com.skydoves.themovies2.extension.getStatusBarSize

object ActivityBinding {

  private fun AppCompatActivity.simpleToolbarWithHome(toolbar: MaterialToolbar, title_: String = "") {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
      title = title_
    }
    if (toolbar.layoutParams is CollapsingToolbarLayout.LayoutParams) {
      toolbar.layoutParams = (toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams).apply {
        topMargin = getStatusBarSize()
      }
    }
  }

  @JvmStatic
  @BindingAdapter("simpleToolbarWithHome", "simpleToolbarTitle")
  fun bindToolbarWithTitle(toolbar: MaterialToolbar, activity: AppCompatActivity, title: String) {
    activity.simpleToolbarWithHome(toolbar, title)
  }
}
