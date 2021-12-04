package com.ayhanunal.movies.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.FeedAdapter
import com.ayhanunal.movies.viewmodel.FeedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed), SearchView.OnQueryTextListener {

    private lateinit var viewModel : FeedViewModel
    private val movieAdapter = FeedAdapter(arrayListOf())

    private var page = 1
    private var isLoading = false
    private var totalPages: Int = 1
    private var lastSearch: String? = null

    override fun onResume() {
        super.onResume()
        //page state returns to top after searching for movies
        page = 1
        lastSearch = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = view.findViewById<SearchView>(R.id.feed_fragment_feed_search_view)
        item.setOnQueryTextListener(this)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData(page)

        view.findViewById<RecyclerView>(R.id.feed_fragment_feed_recycler_view).layoutManager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.feed_fragment_feed_recycler_view).adapter = movieAdapter

        observeLiveData(view)

        view.findViewById<ImageButton>(R.id.fragment_feed_next_page_button).setOnClickListener {
            if (!isLoading && page < totalPages){
                isLoading = true
                page++
                if (lastSearch != null){
                    viewModel.refreshData(page, lastSearch)
                }else{
                    viewModel.refreshData(page)
                }
                view.findViewById<TextView>(R.id.fragment_feed_page_text_view).text = getString(R.string.fragment_feed_page_text, page, totalPages)
            }
        }

        view.findViewById<ImageButton>(R.id.fragment_feed_previous_page_button).setOnClickListener {
            if (!isLoading && page > 1){
                isLoading = true
                page--
                if (lastSearch != null){
                    viewModel.refreshData(page, lastSearch)
                }else{
                    viewModel.refreshData(page)
                }
                view.findViewById<TextView>(R.id.fragment_feed_page_text_view).text = getString(R.string.fragment_feed_page_text, page, totalPages)
            }
        }

        view.findViewById<ImageButton>(R.id.feed_fragment_fav_movies_button).setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToFavMoviesFragment()
            Navigation.findNavController(view).navigate(action)
        }

        view.findViewById<ImageButton>(R.id.feed_fragment_settings_button).setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToSettingsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun observeLiveData(view: View) {
        viewModel.movies.observe(viewLifecycleOwner, Observer {movies ->
            movies?.let {
                if (isLoading) {
                    movieAdapter.updateList(it)
                    isLoading = false
                } else {
                    //Feed Fragment first launch
                    movieAdapter.updateList(it)
                }
            }
        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it) {
                    view.findViewById<AppCompatTextView>(R.id.feed_fragment_feed_error_text_view).visibility = View.VISIBLE
                } else {
                    view.findViewById<AppCompatTextView>(R.id.feed_fragment_feed_error_text_view).visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer {loading ->

            loading?.let {
                if (it) {
                    view.findViewById<ProgressBar>(R.id.feed_fragment_progress_bar).visibility = View.VISIBLE
                    view.findViewById<RecyclerView>(R.id.feed_fragment_feed_recycler_view).visibility = View.GONE
                    view.findViewById<AppCompatTextView>(R.id.feed_fragment_feed_error_text_view).visibility = View.GONE
                } else {
                    view.findViewById<ProgressBar>(R.id.feed_fragment_progress_bar).visibility = View.GONE
                    view.findViewById<RecyclerView>(R.id.feed_fragment_feed_recycler_view).visibility = View.VISIBLE
                }
            }
        })

        viewModel.totalPage.observe(viewLifecycleOwner, Observer {tPages ->
            tPages?.let {
                totalPages = tPages
                view.findViewById<TextView>(R.id.fragment_feed_page_text_view).text = getString(R.string.fragment_feed_page_text, page, totalPages)
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        lastSearch = query
        page = 1
        viewModel.refreshData(page, query)
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.isNullOrEmpty()){
            lastSearch = null
            page = 1
            viewModel.refreshData(page)
        }
        return true
    }

}