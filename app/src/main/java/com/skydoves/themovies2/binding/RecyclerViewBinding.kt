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

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies2.extension.visible
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.view.adapter.MovieListAdapter
import com.skydoves.themovies2.view.adapter.PeopleAdapter
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.TvListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import com.skydoves.themovies2.view.ui.main.MainActivityViewModel
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullOrEmpty

@BindingAdapter("adapter")
fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
  view.adapter = baseAdapter
}

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, movies: List<Movie>?) {
  movies.whatIfNotNull {
    val adapter = view.adapter as? MovieListAdapter
    adapter?.addMovieList(it)
  }
}

@BindingAdapter("paginationMovieList")
fun paginationMovieList(view: RecyclerView, viewModel: MainActivityViewModel) {
  RecyclerViewPaginator(
    recyclerView = view,
    isLoading = { false },
    loadMore = { viewModel.postMoviePage(it) },
    onLast = { false }
  ).run {
    threshold = 4
    currentPage = 1
  }
}

@BindingAdapter("adapterPersonList")
fun bindAdapterPersonList(view: RecyclerView, people: List<Person>?) {
  people.whatIfNotNull {
    val adapter = view.adapter as? PeopleAdapter
    adapter?.addPeople(it)
  }
}

@BindingAdapter("adapterTvList")
fun bindAdapterTvList(view: RecyclerView, tvs: List<Tv>?) {
  tvs.whatIfNotNull {
    val adapter = view.adapter as? TvListAdapter
    adapter?.addTvList(it)
  }
}

@BindingAdapter("adapterVideoList")
fun bindAdapterVideoList(view: RecyclerView, videos: List<Video>?) {
  videos.whatIfNotNullOrEmpty {
    val adapter = view.adapter as? VideoListAdapter
    adapter?.addVideoList(it)
    view.visible()
  }
}

@BindingAdapter("adapterReviewList")
fun bindAdapterReviewList(view: RecyclerView, reviews: List<Review>?) {
  reviews.whatIfNotNullOrEmpty {
    val adapter = view.adapter as? ReviewListAdapter
    adapter?.addReviewList(it)
    view.visible()
  }
}
