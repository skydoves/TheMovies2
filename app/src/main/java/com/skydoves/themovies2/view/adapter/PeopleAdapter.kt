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

package com.skydoves.themovies2.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import com.skydoves.themovies2.R
import com.skydoves.themovies2.databinding.ItemPersonBinding
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.view.ui.details.person.PersonDetailActivity

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

  private val items: MutableList<Person> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
    val binding = parent.binding<ItemPersonBinding>(R.layout.item_person)
    return PeopleViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val person = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        PersonDetailActivity.startActivity(it.context, items[person])
      }
    }
  }

  override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
    with(holder.binding) {
      person = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount(): Int = items.size

  fun addPeople(people: List<Person>) {
    val previousItemSize = items.size
    items.addAll(people)
    notifyItemRangeInserted(previousItemSize, people.size)
  }

  class PeopleViewHolder(val binding: ItemPersonBinding) :
    RecyclerView.ViewHolder(binding.root)
}
