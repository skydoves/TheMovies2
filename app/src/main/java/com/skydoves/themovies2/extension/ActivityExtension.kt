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

@file:Suppress("unused")

package com.skydoves.themovies2.extension

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.skydoves.themovies2.R

fun Activity.circularRevealedAtCenter(view: View) {
  val cx = (view.left + view.right) / 2
  val cy = (view.top + view.bottom) / 2
  val finalRadius = view.width.coerceAtLeast(view.height)

  if (view.isAttachedToWindow) {
    ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius.toFloat())
      .apply {
        view.visible()
        view.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.background))
        duration = 550
        start()
      }
  }
}

fun Activity.requestGlideListener(view: View): RequestListener<Drawable> {
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
      circularRevealedAtCenter(view)
      return false
    }
  }
}

fun AppCompatActivity.simpleToolbarWithHome(toolbar: Toolbar, title_: String = "") {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    setDisplayHomeAsUpEnabled(true)
    setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    title = title_
  }
}

fun AppCompatActivity.applyToolbarMargin(toolbar: Toolbar) {
  toolbar.layoutParams = (toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams).apply {
    topMargin = getStatusBarSize()
  }
}

fun AppCompatActivity.getStatusBarSize(): Int {
  val idStatusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
  return if (idStatusBarHeight > 0) {
    resources.getDimensionPixelSize(idStatusBarHeight)
  } else 0
}
