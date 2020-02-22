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
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.baserecyclerviewadapter.BaseViewHolder
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.models.Video
import kotlinx.android.synthetic.main.item_video.view.*

class VideoListViewHolder(val view: View) : BaseViewHolder(view) {

  private lateinit var video: Video

  override fun bindData(data: Any) {
    if (data is Video) {
      video = data
      drawItem()
    }
  }

  private fun drawItem() {
    itemView.run {
      item_video_title.text = video.name
      Glide.with(context)
        .load(Api.getYoutubeThumbnailPath(video.key))
        .listener(GlidePalette.with(Api.getYoutubeThumbnailPath(video.key))
          .use(BitmapPalette.Profile.VIBRANT)
          .intoBackground(item_video_palette)
          .crossfade(true))
        .into(item_video_cover)
    }
  }

  override fun onClick(v: View?) {
    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(video.key)))
    context().startActivity(playVideoIntent)
  }

  override fun onLongClick(v: View?) = false
}
