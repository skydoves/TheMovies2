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
import com.skydoves.themovies2.databinding.ItemTvBinding
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.view.ui.details.tv.TvDetailActivity

class TvListAdapter : RecyclerView.Adapter<TvListAdapter.TvListViewHolder>() {

  private val items: MutableList<Tv> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvListViewHolder {
    val binding = parent.binding<ItemTvBinding>(R.layout.item_tv)
    return TvListViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val tv = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        TvDetailActivity.startActivityModel(it.context, items[tv])
      }
    }
  }

  override fun onBindViewHolder(holder: TvListViewHolder, position: Int) {
    with(holder.binding) {
      tv = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount(): Int = items.size

  fun addTvList(tvs: List<Tv>) {
    val previousItemSize = items.size
    items.addAll(tvs)
    notifyItemRangeInserted(previousItemSize, tvs.size)
  }

  class TvListViewHolder(val binding: ItemTvBinding) :
    RecyclerView.ViewHolder(binding.root)
}
