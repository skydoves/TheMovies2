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
import com.skydoves.themovies2.api.service.TheDiscoverService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

class TheDiscoverServiceTest : ApiAbstract<TheDiscoverService>() {

  private lateinit var service: TheDiscoverService

  @Before
  fun initService() {
    this.service = createService(TheDiscoverService::class.java)
  }

  @Throws(IOException::class)
  @Test
  fun fetchMovieListTest() = runBlocking {
    enqueueResponse("/tmdb_movie.json")
    when (val response = service.fetchDiscoverMovie(1)) {
      is ApiResponse.Success -> {
        assertThat(response.data?.results?.get(0)?.id, `is`(164558))
        assertThat(response.data?.total_results, `is`(61))
        assertThat(response.data?.total_pages, `is`(4))
      }
    }
  }

  @Throws(IOException::class)
  @Test
  fun fetchTvListTest() = runBlocking {
    enqueueResponse("/tmdb_tv.json")
    when (val response = service.fetchDiscoverTv(1)) {
      is ApiResponse.Success -> {
        assertThat(response.data?.results?.get(0)?.id, `is`(61889))
        assertThat(response.data?.total_results, `is`(61470))
        assertThat(response.data?.total_pages, `is`(3074))
      }
    }
  }
}
