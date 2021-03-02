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

package com.skydoves.themovies2.view.ui.main

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.skydoves.bindables.BindingActivity
import com.skydoves.themovies2.R
import com.skydoves.themovies2.databinding.ActivityMainBinding

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initializeUI()
  }

  private fun initializeUI() {
    with(binding.mainViewpager) {
      adapter = MainPagerAdapter(supportFragmentManager, lifecycle)
      offscreenPageLimit = 3
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(
          position: Int,
          positionOffset: Float,
          positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
          binding.mainBottomNavigation.menu.getItem(position).isChecked = true
        }
      })
      binding.mainBottomNavigation.setOnNavigationItemSelectedListener {
        when (it.itemId) {
          R.id.action_one -> currentItem = 0
          R.id.action_two -> currentItem = 1
          R.id.action_three -> currentItem = 2
        }
        true
      }
    }
  }
}
