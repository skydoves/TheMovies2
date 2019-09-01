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
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.themovies2.R
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityMovieDetailBinding
import com.skydoves.themovies2.extension.applyToolbarMargin
import com.skydoves.themovies2.extension.simpleToolbarWithHome
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import com.skydoves.themovies2.view.viewholder.VideoListViewHolder
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.layout_movie_detail_body.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailActivity : ViewModelActivity(), VideoListViewHolder.Delegate {

  private val vm by viewModel<MovieDetailViewModel>()
  private val binding by binding<ActivityMovieDetailBinding>(R.layout.activity_movie_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    vm.postMovieId(getMovieFromIntent().id)
    with(binding) {
      lifecycleOwner = this@MovieDetailActivity
      viewModel = vm
      detailBody.viewModel = vm
      movie = getMovieFromIntent()
      detailHeader.movie = getMovieFromIntent()
      detailBody.movie = getMovieFromIntent()
    }
    initializeUI()
    observeMessages()
  }

  private fun initializeUI() {
    applyToolbarMargin(movie_detail_toolbar)
    simpleToolbarWithHome(movie_detail_toolbar, getMovieFromIntent().title)
    detail_body_recyclerView_trailers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    detail_body_recyclerView_trailers.adapter = VideoListAdapter(this)
    detail_body_recyclerView_reviews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    detail_body_recyclerView_reviews.adapter = ReviewListAdapter()
    detail_body_recyclerView_reviews.isNestedScrollingEnabled = false
    detail_body_recyclerView_reviews.setHasFixedSize(true)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  override fun onItemClicked(video: Video) {
    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
    startActivity(playVideoIntent)
  }

  private fun getMovieFromIntent() =
    intent.getParcelableExtra(movieId) as Movie

  private fun observeMessages() =
    this.vm.toastLiveData.observe(this) { toast(it) }

  companion object {
    private const val movieId = "movie"
    fun startActivityModel(context: Context?, movie: Movie) {
      context?.startActivity<MovieDetailActivity>(movieId to movie)
    }
  }
}
