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

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import co.lujun.androidtagview.TagContainerLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.extension.requestGlideListener
import com.skydoves.themovies2.extension.visible
import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.models.network.PersonDetail
import com.skydoves.themovies2.utils.KeywordListMapper

@BindingAdapter("visibilityByResource")
fun bindVisibilityByResource(view: View, anyList: List<Any>?) {
  anyList?.let {
    if (it.isNotEmpty()) {
      view.visible()
    }
  }
}

@BindingAdapter("mapKeywordList")
fun bindMapKeywordList(view: TagContainerLayout, keywords: List<Keyword>?) {
  keywords?.let {
    view.tags = KeywordListMapper.mapToStringList(it)
    if (it.isNotEmpty()) {
      view.visible()
    }
  }
}

@BindingAdapter("biography")
fun bindBiography(view: TextView, personDetail: PersonDetail?) {
  view.text = personDetail?.biography
}

@BindingAdapter("nameTags")
fun bindTags(view: TagContainerLayout, personDetail: PersonDetail?) {
  personDetail?.also_known_as?.let {
    view.tags = it
    if (it.isNotEmpty()) {
      view.visible()
    }
  }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindReleaseDate")
fun bindReleaseDate(view: TextView, movie: Movie) {
  view.text = "Release Date : ${movie.release_date}"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("bindAirDate")
fun bindAirDate(view: TextView, tv: Tv) {
  view.text = "First Air Date : ${tv.first_air_date}"
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, movie: Movie) {
  if (movie.backdrop_path != null) {
    Glide.with(view.context).load(Api.getBackdropPath(movie.backdrop_path))
      .listener(view.requestGlideListener())
      .into(view)
  } else {
    Glide.with(view.context).load(Api.getBackdropPath(movie.poster_path!!))
      .listener(view.requestGlideListener())
      .into(view)
  }
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, tv: Tv) {
  if (tv.backdrop_path != null) {
    Glide.with(view.context).load(Api.getBackdropPath(tv.backdrop_path))
      .listener(view.requestGlideListener())
      .into(view)
  } else if (tv.poster_path != null) {
    Glide.with(view.context).load(Api.getBackdropPath(tv.poster_path))
      .listener(view.requestGlideListener())
      .into(view)
  }
}

@BindingAdapter("bindBackDrop")
fun bindBackDrop(view: ImageView, person: Person) {
  if (person.profile_path != null) {
    Glide.with(view.context).load(Api.getBackdropPath(person.profile_path))
      .apply(RequestOptions().circleCrop())
      .into(view)
  }
}
