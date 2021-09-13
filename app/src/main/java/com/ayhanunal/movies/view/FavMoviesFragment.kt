package com.ayhanunal.movies.view

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.FeedAdapter
import com.ayhanunal.movies.viewmodel.FavMoviesViewModel

class FavMoviesFragment : Fragment(R.layout.fragment_fav_movies) {

    private val movieAdapter = FeedAdapter(arrayListOf())
    private lateinit var viewModel : FavMoviesViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FavMoviesViewModel::class.java)
        viewModel.refreshData()

        view.findViewById<RecyclerView>(R.id.fav_movies_fragment_feed_recycler_view).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.fav_movies_fragment_feed_recycler_view).adapter = movieAdapter

        observeLiveData(view)

    }

    private fun observeLiveData(view: View) {
        viewModel.movies.observe(viewLifecycleOwner, Observer {movies ->
            movies?.let {
                movieAdapter.updateList(it)
            }
        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it) {
                    view.findViewById<AppCompatTextView>(R.id.fav_movies_fragment_feed_error_text_view).visibility = View.VISIBLE
                } else {
                    view.findViewById<AppCompatTextView>(R.id.fav_movies_fragment_feed_error_text_view).visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if (it) {
                    view.findViewById<ProgressBar>(R.id.fav_movies_fragment_progress_bar).visibility = View.VISIBLE
                    view.findViewById<RecyclerView>(R.id.fav_movies_fragment_feed_recycler_view).visibility = View.GONE
                    view.findViewById<AppCompatTextView>(R.id.fav_movies_fragment_feed_error_text_view).visibility = View.GONE
                } else {
                    view.findViewById<ProgressBar>(R.id.fav_movies_fragment_progress_bar).visibility = View.GONE
                    view.findViewById<RecyclerView>(R.id.fav_movies_fragment_feed_recycler_view).visibility = View.VISIBLE
                }
            }
        })
    }

}