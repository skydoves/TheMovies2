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
package com.skydoves.themovies2.api

import okhttp3.ResponseBody
import retrofit2.Response

@Suppress("unused", "HasPlatformType", "SpellCheckingInspection", "MemberVisibilityCanBePrivate")
sealed class ApiResponse<out T> {

  /**
   * API Success response class from retrofit.
   *
   * [data] is optional. (There are responses without data)
   */
  class Success<T>(response: Response<T>) : ApiResponse<T>() {
    val data: T? = response.body()
    override fun toString() = "[ApiResponse.Success]: $data"
  }

  /**
   * API Failure response class.
   *
   * ## Throw Exception case.
   * Gets called when an unexpected exception occurs while creating the request or processing the response.
   *
   * ## API Network format error case.
   * API communication conventions do not match or applications need to handle errors.
   *
   * ## API Network Excepttion error case.
   * Gets called when an unexpected exception occurs while creating the request or processing the response.
   */
  sealed class Failure<out T> {
    class Error<out T>(response: Response<out T>) : ApiResponse<T>() {
      val responseBody: ResponseBody? = response.errorBody()
      val code: Int = response.code()
      override fun toString(): String = "[ApiResponse.Failure $code]: $responseBody"
    }

    class Exception<out T>(exception: Throwable) : ApiResponse<T>() {
      val message: String? = exception.localizedMessage
      override fun toString(): String = "[ApiResponse.Failure]: $message"
    }
  }

  companion object {
    /**
     * ApiResponse Factory
     *
     * [Failure] factory function. Only receives [Throwable] arguments.
     */
    fun <T> error(ex: Throwable) = Failure.Exception<T>(ex)

    /**
     * ApiResponse Factory
     *
     * [f] Create ApiResponse from [retrofit2.Response] returning from the block.
     * If [retrofit2.Response] has no errors, it will create [ApiResponse.Success]
     * If [retrofit2.Response] has errors, it will create [ApiResponse.Failure.Error]
     */
    fun <T> of(f: () -> Response<T>): ApiResponse<T> = try {
      val response = f()
      if (response.isSuccessful) {
        Success(response)
      } else {
        Failure.Error(response)
      }
    } catch (ex: Exception) {
      Failure.Exception(ex)
    }
  }
}
