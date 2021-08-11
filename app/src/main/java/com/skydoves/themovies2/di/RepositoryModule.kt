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

package com.skydoves.themovies2.di

import com.skydoves.themovies2.api.service.MovieService
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.api.service.TvService
import com.skydoves.themovies2.repository.DiscoverRepository
import com.skydoves.themovies2.repository.MovieRepository
import com.skydoves.themovies2.repository.PeopleRepository
import com.skydoves.themovies2.repository.TvRepository
import com.skydoves.themovies2.room.MovieDao
import com.skydoves.themovies2.room.PeopleDao
import com.skydoves.themovies2.room.TvDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideDiscoverRepository(
    discoverService: TheDiscoverService,
    movieDao: MovieDao,
    tvDao: TvDao
  ): DiscoverRepository {
    return DiscoverRepository(discoverService, movieDao, tvDao)
  }

  @Provides
  @ViewModelScoped
  fun provideMovieRepository(
    movieService: MovieService,
    movieDao: MovieDao
  ): MovieRepository {
    return MovieRepository(movieService, movieDao)
  }

  @Provides
  @ViewModelScoped
  fun providePeopleRepository(
    peopleService: PeopleService,
    peopleDao: PeopleDao
  ): PeopleRepository {
    return PeopleRepository(peopleService, peopleDao)
  }

  @Provides
  @ViewModelScoped
  fun provideTvRepository(
    tvService: TvService,
    tvDao: TvDao
  ): TvRepository {
    return TvRepository(tvService, tvDao)
  }
}
