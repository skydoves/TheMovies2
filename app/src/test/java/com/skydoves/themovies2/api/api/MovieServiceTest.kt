/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.skydoves.themovies2.api.api

import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.async
import com.skydoves.themovies2.api.service.MovieService
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

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
    this.service.fetchKeywords(1).async {
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
    this.service.fetchVideos(1).async {
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
    this.service.fetchReviews(1).async {
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
