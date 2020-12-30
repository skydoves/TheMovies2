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

package com.skydoves.themovies2.view.ui.details.tv

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityTvDetailBinding
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import org.koin.android.viewmodel.ext.android.getViewModel

class TvDetailActivity : ViewModelActivity() {

  private val binding: ActivityTvDetailBinding by binding(R.layout.activity_tv_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val intentTv = intent.getParcelableExtra<Tv>(tvId) as Tv
    with(binding) {
      activity = this@TvDetailActivity
      lifecycleOwner = this@TvDetailActivity
      viewModel = getViewModel(TvDetailViewModel::class).apply { postTvId(intentTv.id) }
      tv = intentTv
      videoAdapter = VideoListAdapter()
      reviewAdapter = ReviewListAdapter()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    private const val tvId = "tv"
    fun startActivityModel(context: Context?, tv: Tv) {
      if (context is Activity) {
        context.startActivity(Intent(context, TvDetailActivity::class.java).putExtra(tvId, tv))
      }
    }
  }
}
