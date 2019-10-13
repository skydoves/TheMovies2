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

package com.skydoves.themovies2.utils

import com.skydoves.themovies2.models.Keyword
import com.skydoves.themovies2.models.Review
import com.skydoves.themovies2.models.Video
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.models.entity.Person
import com.skydoves.themovies2.models.entity.Tv
import com.skydoves.themovies2.models.network.PersonDetail

class MockTestUtil {
  companion object {
    fun mockMovie(keywords: List<Keyword> = emptyList(), videos: List<Video> = emptyList(), reviews: List<Review> = emptyList()) = Movie(1, keywords, videos, reviews, "", false, "", "", ArrayList(), 123, "", "", "", "", 0f, 0, false, 0f)
    fun mockTv(keywords: List<Keyword> = emptyList(), videos: List<Video> = emptyList(), reviews: List<Review> = emptyList()) = Tv(1, keywords, videos, reviews, "", 0f, 123, "", 0f, "", "", ArrayList(), ArrayList(), "", 1, "", "")
    fun mockPerson() = Person(1, mockPersonDetail(), "", false, 123, "", 0f)
    fun mockMovieList(): List<Movie> {
      val movies = ArrayList<Movie>()
      movies.add(mockMovie())
      movies.add(mockMovie())
      movies.add(mockMovie())
      return movies
    }

    fun mockTvList(): List<Tv> {
      val tvs = ArrayList<Tv>()
      tvs.add(mockTv())
      tvs.add(mockTv())
      tvs.add(mockTv())
      return tvs
    }

    fun mockPersonList(): List<Person> {
      val people = ArrayList<Person>()
      people.add(mockPerson())
      people.add(mockPerson())
      people.add(mockPerson())
      return people
    }

    fun mockKeywordList(): List<Keyword> {
      val keywords = ArrayList<Keyword>()
      keywords.add(Keyword(100, "keyword0"))
      keywords.add(Keyword(101, "keyword1"))
      keywords.add(Keyword(102, "keyword2"))
      return keywords
    }

    fun mockVideoList(): List<Video> {
      val videos = ArrayList<Video>()
      videos.add(Video("123", "video0", "", "", 0, ""))
      videos.add(Video("123", "video0", "", "", 0, ""))
      return videos
    }

    fun mockReviewList(): List<Review> {
      val reviews = ArrayList<Review>()
      reviews.add(Review("123", "", "", ""))
      reviews.add(Review("123", "", "", ""))
      return reviews
    }

    fun mockPersonDetail(): PersonDetail {
      return PersonDetail("", "", "", emptyList(), "")
    }
  }
}
