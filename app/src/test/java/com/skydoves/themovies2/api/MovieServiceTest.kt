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

package com.skydoves.themovies2.api

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.request
import com.skydoves.themovies2.api.service.MovieService
import java.io.IOException
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class MovieServiceTest : ApiAbstract<MovieService>() {

  private lateinit var service: MovieService

  @Before
  fun initService() {
    this.service = createService(MovieService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieKeywordsTest() {
    enqueueResponse("/tmdb_movie_keywords.json")
    this.service.fetchKeywords(1).request {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(550))
          assertThat(it.data?.keywords?.get(0)?.id, `is`(825))
          assertThat(it.data?.keywords?.get(0)?.name, `is`("support group"))
        }
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieVideosTest() {
    enqueueResponse("/tmdb_movie_videos.json")
    this.service.fetchVideos(1).request {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(550))
          assertThat(it.data?.results?.get(0)?.id, `is`("533ec654c3a36854480003eb"))
          assertThat(it.data?.results?.get(0)?.key, `is`("SUXWAEX2jlg"))
        }
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieReviewsTest() {
    enqueueResponse("/tmdb_movie_reviews.json")
    this.service.fetchReviews(1).request {
      when (it) {
        is ApiResponse.Success -> {
          assertThat(it.data?.id, `is`(297761))
          assertThat(it.data?.results?.get(0)?.id, `is`("57a814dc9251415cfb00309a"))
          assertThat(it.data?.results?.get(0)?.author, `is`("Frank Ochieng"))
        }
      }
    }
  }
}
