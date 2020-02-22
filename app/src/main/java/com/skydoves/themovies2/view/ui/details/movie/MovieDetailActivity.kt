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

package com.skydoves.themovies2.view.ui.details.movie

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityMovieDetailBinding
import com.skydoves.themovies2.extension.applyToolbarMargin
import com.skydoves.themovies2.extension.simpleToolbarWithHome
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : ViewModelActivity() {

  private val vm: MovieDetailViewModel by viewModel()
  private val binding: ActivityMovieDetailBinding by binding(R.layout.activity_movie_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    with(binding) {
      lifecycleOwner = this@MovieDetailActivity
      viewModel = vm.apply { postMovieId(getMovieFromIntent().id) }
      movie = getMovieFromIntent()
      videoListAdapter = VideoListAdapter()
      reviewListAdapter = ReviewListAdapter()
    }
    initializeUI()
  }

  private fun initializeUI() {
    applyToolbarMargin(movie_detail_toolbar)
    simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  private fun getMovieFromIntent() =
    intent.getParcelableExtra(movieId) as Movie

  companion object {
    private const val movieId = "movie"
    fun startActivityModel(context: Context?, movie: Movie) {
      context?.startActivity<MovieDetailActivity>(movieId to movie)
    }
  }
}
