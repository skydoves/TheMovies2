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

package com.skydoves.themovies2.view.viewholder

import android.content.Intent
import android.net.Uri
import android.view.View
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.databinding.ItemVideoBinding
import com.skydoves.themovies2.models.Video

class VideoListViewHolder(val view: View) : BaseViewHolder(view) {

  private val binding: ItemVideoBinding by bindings(view)

  override fun bindData(data: Any) {
    if (data is Video) {
      binding.video = data
      binding.executePendingBindings()
    }
  }

  override fun onClick(v: View?) {
    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(binding.video?.key)))
    context.startActivity(playVideoIntent)
  }

  override fun onLongClick(v: View?) = false
}
