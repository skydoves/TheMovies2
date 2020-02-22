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

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelActivity
import com.skydoves.themovies2.databinding.ActivityTvDetailBinding
import com.skydoves.themovies2.extension.applyToolbarMargin
import com.skydoves.themovies2.extension.simpleToolbarWithHome
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.view.adapter.ReviewListAdapter
import com.skydoves.themovies2.view.adapter.VideoListAdapter
import kotlinx.android.synthetic.main.activity_tv_detail.*
import kotlinx.android.synthetic.main.layout_tv_detail_body.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class TvDetailActivity : ViewModelActivity() {

  private val vm by viewModel<TvDetailViewModel>()
  private val binding by binding<ActivityTvDetailBinding>(R.layout.activity_tv_detail)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    vm.postTvId(getTvFromIntent().id)
    with(binding) {
      lifecycleOwner = this@TvDetailActivity
      viewModel = vm
      detailBody.viewModel = vm
      tv = getTvFromIntent()
      detailHeader.tv = getTvFromIntent()
      detailBody.tv = getTvFromIntent()
    }
    initializeUI()
    observeMessages()
  }

  private fun initializeUI() {
    applyToolbarMargin(tv_detail_toolbar)
    simpleToolbarWithHome(tv_detail_toolbar, getTvFromIntent().name)
    detail_body_recyclerView_trailers.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    detail_body_recyclerView_trailers.adapter = VideoListAdapter()
    detail_body_recyclerView_reviews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    detail_body_recyclerView_reviews.adapter = ReviewListAdapter()
    detail_body_recyclerView_reviews.isNestedScrollingEnabled = false
    detail_body_recyclerView_reviews.setHasFixedSize(true)
  }

  private fun getTvFromIntent() =
    intent.getParcelableExtra(tvId) as Tv

  private fun observeMessages() =
    this.vm.toastLiveData.observe(this) { toast(it) }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item?.itemId == android.R.id.home) onBackPressed()
    return false
  }

  companion object {
    private const val tvId = "tv"
    fun startActivityModel(context: Context?, tv: Tv) {
      context?.startActivity<TvDetailActivity>(tvId to tv)
    }
  }
}
