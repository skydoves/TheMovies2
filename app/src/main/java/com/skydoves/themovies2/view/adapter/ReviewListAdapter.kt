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
import com.skydoves.themovies2.databinding.ItemReviewBinding
import com.skydoves.themovies2.models.Review

class ReviewListAdapter : RecyclerView.Adapter<ReviewListAdapter.ReviewListViewHolder>() {

  private val items: MutableList<Review> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewListViewHolder {
    val binding = parent.binding<ItemReviewBinding>(R.layout.item_review)
    return ReviewListViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
    with(holder.binding) {
      review = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount(): Int = items.size

  fun addReviewList(reviews: List<Review>) {
    items.addAll(reviews)
    notifyItemRangeInserted(items.size + 1, reviews.size)
  }

  class ReviewListViewHolder(val binding: ItemReviewBinding) :
    RecyclerView.ViewHolder(binding.root)
}
