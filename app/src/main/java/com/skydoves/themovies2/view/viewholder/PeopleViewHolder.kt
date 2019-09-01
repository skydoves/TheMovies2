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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.models.entity.Person
import kotlinx.android.synthetic.main.item_person.view.*

class PeopleViewHolder(
  val view: View,
  private val delegate: Delegate
) : BaseViewHolder(view) {

  interface Delegate {
    fun onItemClick(person: Person, view: View)
  }

  private lateinit var person: Person

  @Throws(Exception::class)
  override fun bindData(data: Any) {
    if (data is Person) {
      person = data
      drawItem()
    }
  }

  private fun drawItem() {
    itemView.run {
      item_person_name.text = person.name
      person.profile_path?.let {
        Glide.with(context)
          .load(Api.getPosterPath(it))
          .apply(RequestOptions().circleCrop())
          .into(item_person_profile)
      }
    }
  }

  override fun onClick(p0: View?) =
    delegate.onItemClick(person, itemView.item_person_profile)

  override fun onLongClick(p0: View?) = false
}
