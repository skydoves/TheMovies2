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

package com.skydoves.themovies2.view.adapter

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.bindables.binding
import com.skydoves.themovies2.R
import com.skydoves.themovies2.api.Api
import com.skydoves.themovies2.databinding.ItemVideoBinding
import com.skydoves.themovies2.models.Video

class VideoListAdapter : RecyclerView.Adapter<VideoListAdapter.VideoListViewHolder>() {

  private val items: MutableList<Video> = arrayListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
    val binding = parent.binding<ItemVideoBinding>(R.layout.item_video)
    return VideoListViewHolder(binding).apply {
      binding.root.setOnClickListener {
        val video = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
          ?: return@setOnClickListener
        val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Api.getYoutubeVideoPath(items[video].key)))
        it.context.startActivity(playVideoIntent)
      }
    }
  }

  override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
    with(holder.binding) {
      video = items[position]
      executePendingBindings()
    }
  }

  override fun getItemCount(): Int = items.size

  fun addVideoList(videos: List<Video>) {
    items.addAll(videos)
    notifyItemRangeInserted(items.size + 1, videos.size)
  }

  class VideoListViewHolder(val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root)
}
