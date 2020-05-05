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

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.skydoves.themovies2.api.RequestInterceptor
import com.skydoves.themovies2.api.service.MovieService
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.api.service.TheDiscoverService
import com.skydoves.themovies2.api.service.TvService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
  single {
    OkHttpClient.Builder()
      .addInterceptor(RequestInterceptor())
      .addNetworkInterceptor(StethoInterceptor())
      .build()
  }

  single {
    Retrofit.Builder()
      .client(get<OkHttpClient>())
      .baseUrl("https://api.themoviedb.org/")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  single {
    get<Retrofit>().create(TheDiscoverService::class.java)
  }

  single {
    get<Retrofit>().create(PeopleService::class.java)
  }

  single {
    get<Retrofit>().create(MovieService::class.java)
  }

  single {
    get<Retrofit>().create(TvService::class.java)
  }
}
