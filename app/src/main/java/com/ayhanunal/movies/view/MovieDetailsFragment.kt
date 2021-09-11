package com.ayhanunal.movies.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.CastAdapter
import com.ayhanunal.movies.adapter.VideosAdapter
import com.ayhanunal.movies.databinding.FragmentMovieDetailsBinding
import com.ayhanunal.movies.util.downloadImage
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*


class MovieDetailsFragment : Fragment() {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w780"

    private var movieId = 0
    private var adapter = CastAdapter(arrayListOf())
    private var videosAdapter = VideosAdapter(arrayListOf())

    private lateinit var dataBinding: FragmentMovieDetailsBinding
    private lateinit var viewModel : MovieDetailsViewModel
    private lateinit var movieLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            movieId = MovieDetailsFragmentArgs.fromBundle(it).movieID
        }

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        viewModel.refreshData(movieId)

        observeLiveData()

        movie_details_fab.setOnClickListener {
            if (!movieLink.isNullOrEmpty()){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movieLink))
                startActivity(browserIntent)
            }
        }
    }

    private fun observeLiveData() {
        viewModel.actors.observe(viewLifecycleOwner, Observer {
            adapter = CastAdapter(it)
            movie_details_cast_recycler_view.layoutManager = GridLayoutManager(context, 4)
            movie_details_cast_recycler_view.adapter = adapter
        })

        viewModel.detail.observe(viewLifecycleOwner, Observer {
            dataBinding.movieDetail = it

            context?.let {context ->
                movieLink = it.homepage
                movie_details_detail_image.downloadImage(IMAGE_PATH + it.backdrop_path, placeholderProgressBar(context))
            }

        })

        viewModel.movieDetailLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                movie_details_movie_detail_container.visibility = View.GONE
                movie_details_loading_progress_bar.visibility = View.VISIBLE
            } else {
                movie_details_movie_detail_container.visibility = View.VISIBLE
                movie_details_loading_progress_bar.visibility = View.GONE
            }
        })

        viewModel.actorsLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                movie_details_cast_recycler_view.visibility = View.GONE
            } else {
                movie_details_cast_recycler_view.visibility = View.VISIBLE
            }
        })

        viewModel.videos.observe(viewLifecycleOwner, Observer {
            videosAdapter = VideosAdapter(it.results)
            movie_details_actor_videos_recycler_view.layoutManager = GridLayoutManager(context, 3)
            movie_details_actor_videos_recycler_view.adapter = videosAdapter
        })
    }

}