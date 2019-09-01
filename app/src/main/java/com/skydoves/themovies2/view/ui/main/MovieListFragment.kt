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

package com.skydoves.themovies2.view.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.themovies2.R
import com.skydoves.themovies2.compose.ViewModelFragment
import com.skydoves.themovies2.databinding.MainFragmentMovieBinding
import com.skydoves.themovies2.models.entity.Movie
import com.skydoves.themovies2.view.adapter.MovieListAdapter
import com.skydoves.themovies2.view.ui.details.movie.MovieDetailActivity
import com.skydoves.themovies2.view.viewholder.MovieListViewHolder
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.toast
import org.koin.android.viewmodel.ext.android.viewModel

class MovieListFragment : ViewModelFragment(), MovieListViewHolder.Delegate {

  private val viewModel by viewModel<MainActivityViewModel>()
  private lateinit var binding: MainFragmentMovieBinding

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = binding(inflater, R.layout.main_fragment_movie, container)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeUI()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    loadMore(page = 1)
    observeMessages()
  }

  private fun initializeUI() {
    recyclerView.adapter = MovieListAdapter(this)
    recyclerView.layoutManager = GridLayoutManager(context, 2)
    RecyclerViewPaginator(
      recyclerView = recyclerView,
      isLoading = { false },
      loadMore = { loadMore(it) },
      onLast = { false }
    ).apply {
      threshold = 4
      currentPage = 1
    }
  }

  private fun loadMore(page: Int) = viewModel.postMoviePage(page)

  override fun onItemClick(movie: Movie) =
    MovieDetailActivity.startActivityModel(context, movie)

  private fun observeMessages() =
    viewModel.toastLiveData.observe(this) { toast(it) }
}
