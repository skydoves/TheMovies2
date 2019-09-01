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

package com.skydoves.themovies2.api.client

import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.async
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.models.network.KeywordListResponse
import com.skydoves.themovies2.models.network.ReviewListResponse
import com.skydoves.themovies2.models.network.VideoListResponse

class TvClient(private val service: TvService) {

  fun fetchKeywords(
    id: Int,
    onResult: (response: ApiResponse<KeywordListResponse>) -> Unit
  ) {
    service.fetchKeywords(id).async(onResult)
  }

  fun fetchVideos(
    id: Int,
    onResult: (response: ApiResponse<VideoListResponse>) -> Unit
  ) {
    service.fetchVideos(id).async(onResult)
  }

  fun fetchReviews(
    id: Int,
    onResult: (response: ApiResponse<ReviewListResponse>) -> Unit
  ) {
    service.fetchReviews(id).async(onResult)
  }
}
