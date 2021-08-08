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

package com.skydoves.themovies2.extension

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.skydoves.themovies2.R
import kotlin.math.max

fun View.visible() {
  visibility = View.VISIBLE
}

fun View.requestGlideListener(): RequestListener<Drawable> {
  return object : RequestListener<Drawable> {
    override fun onLoadFailed(
      e: GlideException?,
      model: Any?,
      target: Target<Drawable>?,
      isFirstResource: Boolean
    ): Boolean {
      return false
    }

    override fun onResourceReady(
      resource: Drawable?,
      model: Any?,
      target: Target<Drawable>?,
      dataSource: DataSource?,
      isFirstResource: Boolean
    ): Boolean {
      circularRevealedAtCenter()
      return false
    }
  }
}

fun View.circularRevealedAtCenter() {
  val view = this
  val cx = (view.left + view.right) / 2
  val cy = (view.top + view.bottom) / 2
  val finalRadius = max(view.width, view.height)

  if (view.isAttachedToWindow) {
    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
      .apply {
        view.visible()
        view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.background))
        duration = 550
        start()
      }
  }
}

fun ViewPager2.applyOnPageSelected(onPageSelected: (Int) -> Unit) {
  registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
    override fun onPageScrollStateChanged(state: Int) = Unit
    override fun onPageScrolled(
      position: Int,
      positionOffset: Float,
      positionOffsetPixels: Int
    ) = Unit

    override fun onPageSelected(position: Int) = onPageSelected.invoke(position)
  })
}
