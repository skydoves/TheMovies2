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

package com.skydoves.themovies2.api.client

import com.skydoves.themovies2.api.ApiResponse
import com.skydoves.themovies2.api.async
import com.skydoves.themovies2.api.service.PeopleService
import com.skydoves.themovies2.models.network.PeopleResponse
import com.skydoves.themovies2.models.network.PersonDetail

class PeopleClient(private val service: PeopleService) {

  fun fetchPopularPeople(
    page: Int,
    onResult: (response: ApiResponse<PeopleResponse>) -> Unit
  ) {
    service.fetchPopularPeople(page).async(onResult)
  }

  fun fetchPersonDetail(
    page: Int,
    onResult: (response: ApiResponse<PersonDetail>) -> Unit
  ) {
    service.fetchPersonDetail(page).async(onResult)
  }
}
