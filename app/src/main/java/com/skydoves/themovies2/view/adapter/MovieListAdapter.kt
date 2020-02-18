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

import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.SectionRow
import com.skydoves.themovies2.R
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.view.viewholder.MovieListViewHolder

class MovieListAdapter : BaseAdapter() {

  init {
    addSection(ArrayList<Movie>())
  }

  fun addMovieList(movies: List<Movie>) {
    val section = sections()[0]
    section.addAll(movies)
    notifyItemRangeInserted(section.size + 1, movies.size)
  }

  override fun layout(sectionRow: SectionRow) = R.layout.item_poster

  override fun viewHolder(layout: Int, view: View) = MovieListViewHolder(view)
}
