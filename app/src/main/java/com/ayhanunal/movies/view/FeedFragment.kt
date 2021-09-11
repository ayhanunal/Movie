package com.ayhanunal.movies.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.FeedAdapter
import com.ayhanunal.movies.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed), SearchView.OnQueryTextListener {

    private lateinit var viewModel : FeedViewModel
    private val movieAdapter = FeedAdapter(arrayListOf())

    private var page = 1
    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = view.findViewById<SearchView>(R.id.feed_fragment_feed_search_view)
        item.setOnQueryTextListener(this)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData(page)

        feed_fragment_feed_recycler_view.layoutManager = LinearLayoutManager(context)
        feed_fragment_feed_recycler_view.adapter = movieAdapter

        observeLiveData()

        checkRecyclerViewPagination()

    }

    private fun observeLiveData() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {movies ->
            movies?.let {
                if (isLoading) {
                    movieAdapter.addPaginationList(it)
                    isLoading = false
                } else {
                    movieAdapter.updateList(it)
                }
            }
        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it) {
                    feed_fragment_feed_error_text_view.visibility = View.VISIBLE
                } else {
                    feed_fragment_feed_error_text_view.visibility = View.GONE
                }
            }
        })

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer {loading ->

            loading?.let {
                if (it) {
                    feed_fragment_progress_bar.visibility = View.VISIBLE
                    feed_fragment_feed_recycler_view.visibility = View.GONE
                    feed_fragment_feed_error_text_view.visibility = View.GONE
                } else {
                    feed_fragment_progress_bar.visibility = View.GONE
                    feed_fragment_feed_recycler_view.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun checkRecyclerViewPagination() {

        feed_fragment_feed_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true
                    page++
                    viewModel.refreshData(page)
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrEmpty()) {
            page = 1
            viewModel.refreshData(page)
        } else {
            viewModel.refreshData(page, query)
        }
        return true

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.isNullOrEmpty()){
            page = 1
            viewModel.refreshData(page)
        }
        return true
    }

}