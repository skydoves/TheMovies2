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

package com.skydoves.themovies2.view.viewholder

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies2.databinding.ItemPersonBinding
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.view.ui.details.person.PersonDetailActivity

class PeopleViewHolder(val view: View) : BaseViewHolder(view) {

  private val binding: ItemPersonBinding by bindings(view)

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is Person) {
      binding.person = data
      binding.executePendingBindings()
    }
  }

  override fun onClick(p0: View?) =
    PersonDetailActivity.startActivity(context, binding.person, binding.itemPersonProfile)

  override fun onLongClick(p0: View?) = false
}
