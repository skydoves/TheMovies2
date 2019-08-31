/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.skydoves.themovies2.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
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

@BindingAdapter("adapterMovieList")
fun bindAdapterMovieList(view: RecyclerView, movies: List<Movie>?) {
  movies?.let {
    val adapter = view.adapter as? MovieListAdapter
    adapter?.addMovieList(it)
  }
}

@BindingAdapter("adapterPersonList")
fun bindAdapterPersonList(view: RecyclerView, people: List<Person>?) {
  people?.let {
    val adapter = view.adapter as? PeopleAdapter
    adapter?.addPeople(it)
  }
}

@BindingAdapter("adapterTvList")
fun bindAdapterTvList(view: RecyclerView, tvs: List<Tv>?) {
  tvs?.let {
    val adapter = view.adapter as? TvListAdapter
    adapter?.addTvList(it)
  }
}

@BindingAdapter("adapterVideoList")
fun bindAdapterVideoList(view: RecyclerView, videos: List<Video>?) {
  videos?.let {
    val adapter = view.adapter as? VideoListAdapter
    adapter?.addVideoList(it)
    if (it.isNotEmpty()) {
      view.visible()
    }
  }
}

@BindingAdapter("adapterReviewList")
fun bindAdapterReviewList(view: RecyclerView, reviews: List<Review>?) {
  reviews?.let {
    val adapter = view.adapter as? ReviewListAdapter
    adapter?.addReviewList(it)
    if (it.isNotEmpty()) {
      view.visible()
    }
  }
}
