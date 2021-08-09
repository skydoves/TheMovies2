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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.skydoves.baserecyclerviewadapter.BaseAdapter
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies2.R
import com.skydoves.themovies2.extension.visible
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.view.adapter.MovieListAdapter
import com.skydoves.themovies2.view.adapter.PeopleAdapter
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.TvListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import com.skydoves.themovies2.view.ui.main.MainActivityViewModel
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullAs
import com.skydoves.whatif.whatIfNotNullOrEmpty

object RecyclerViewBinding {

  @JvmStatic
  @BindingAdapter("adapter")
  fun bindAdapter(view: RecyclerView, baseAdapter: BaseAdapter) {
    view.adapter = baseAdapter
  }

  @JvmStatic
  @BindingAdapter("adapterMovieList")
  fun bindAdapterMovieList(view: RecyclerView, movies: List<Movie>?) {
    movies.whatIfNotNull {
      (view.adapter as? MovieListAdapter)?.addMovieList(it)
    }
  }

  @JvmStatic
  @BindingAdapter("paginationMovieList")
  fun paginationMovieList(view: RecyclerView, viewModel: MainActivityViewModel) {
    RecyclerViewPaginator(
      recyclerView = view,
      isLoading = { viewModel.isMovieListLoading },
      loadMore = { viewModel.postMoviePage(it) },
      onLast = { false }
    ).run {
      threshold = 4
      currentPage = 1
    }
  }

  @JvmStatic
  @BindingAdapter("adapterPersonList")
  fun bindAdapterPersonList(view: RecyclerView, people: List<Person>?) {
    people.whatIfNotNull { items ->
      view.adapter.whatIfNotNullAs<PeopleAdapter> {
        it.addPeople(items)
      }
    }
  }

  @JvmStatic
  @BindingAdapter("paginationPersonList")
  fun paginationPersonList(view: RecyclerView, viewModel: MainActivityViewModel) {
    RecyclerViewPaginator(
      recyclerView = view,
      isLoading = { viewModel.isPeopleListLoading },
      loadMore = { viewModel.postPeoplePage(it) },
      onLast = { false }
    ).run {
      threshold = 4
      currentPage = 1
    }
  }

  @JvmStatic
  @BindingAdapter("adapterTvList")
  fun bindAdapterTvList(view: RecyclerView, tvs: List<Tv>?) {
    tvs.whatIfNotNull { items ->
      view.adapter.whatIfNotNullAs<TvListAdapter> {
        it.addTvList(items)
      }
    }
  }

  @JvmStatic
  @BindingAdapter("paginationTvList")
  fun paginationTvList(view: RecyclerView, viewModel: MainActivityViewModel) {
    RecyclerViewPaginator(
      recyclerView = view,
      isLoading = { viewModel.isTvListLoading },
      loadMore = { viewModel.postTvPage(it) },
      onLast = { false }
    ).run {
      threshold = 4
      currentPage = 1
    }
  }

  @JvmStatic
  @BindingAdapter("adapterVideoList")
  fun bindAdapterVideoList(view: RecyclerView, videos: List<Video>?) {
    videos.whatIfNotNullOrEmpty { items ->
      view.adapter.whatIfNotNullAs<VideoListAdapter> {
        it.addVideoList(items)
        view.visible()
      }
    }
  }

  @JvmStatic
  @BindingAdapter("adapterReviewList")
  fun bindAdapterReviewList(view: RecyclerView, reviews: List<Review>?) {
    view.setHasFixedSize(true)
    reviews.whatIfNotNullOrEmpty { items ->
      view.adapter.whatIfNotNullAs<ReviewListAdapter> {
        it.addReviewList(items)
        view.visible()
      }
    }
  }

  @JvmStatic
  @BindingAdapter("mapKeywordList")
  fun bindMapKeywordList(chipGroup: ChipGroup, keywords: List<Keyword>?) {
    keywords.whatIfNotNullOrEmpty {
      chipGroup.visible()
      for (keyword in it) {
        chipGroup.addView(
          Chip(chipGroup.context).apply {
            text = keyword.name
            isCheckable = false
            setTextAppearanceResource(R.style.ChipTextStyle)
            setChipBackgroundColorResource(R.color.colorPrimary)
          }
        )
      }
    }
  }

  @JvmStatic
  @BindingAdapter("mapNameTagList")
  fun bindTags(chipGroup: ChipGroup, personDetail: PersonDetail?) {
    personDetail?.also_known_as.whatIfNotNull {
      chipGroup.visible()
      for (nameTag in it) {
        chipGroup.addView(
          Chip(chipGroup.context).apply {
            text = nameTag
            isCheckable = false
            setTextAppearanceResource(R.style.ChipTextStyle)
            setChipBackgroundColorResource(R.color.colorPrimary)
          }
        )
      }
    }
  }
}
