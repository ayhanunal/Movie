package com.ayhanunal.movies.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.CastAdapter
import com.ayhanunal.movies.adapter.VideosAdapter
import com.ayhanunal.movies.databinding.FragmentMovieDetailsBinding
import com.ayhanunal.movies.viewmodel.MovieDetailsViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MovieDetailsFragment : Fragment() {

    private var movieId = 0
    private var castAdapter = CastAdapter(arrayListOf())
    private var videosAdapter = VideosAdapter(arrayListOf())

    private lateinit var dataBinding: FragmentMovieDetailsBinding
    private lateinit var viewModel : MovieDetailsViewModel
    private lateinit var movieLink: String
    private var isFavMovie: Boolean = false

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

        observeLiveData(view)

        view.findViewById<FloatingActionButton>(R.id.movie_details_fab).setOnClickListener {
            if (!movieLink.isEmpty()){
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movieLink))
                startActivity(browserIntent)
            }
        }

        view.findViewById<ImageButton>(R.id.movie_details_movie_fav_button).setOnClickListener {
            if (isFavMovie){
                //deactive
                viewModel.favOrUnfavMovie(false)
                view.findViewById<ImageButton>(R.id.movie_details_movie_fav_button).setImageDrawable(resources.getDrawable(R.drawable.fav_movie_icon_active))
            }else{
                //active
                viewModel.favOrUnfavMovie(true)
                view.findViewById<ImageButton>(R.id.movie_details_movie_fav_button).setImageDrawable(resources.getDrawable(R.drawable.fav_movie_icon_deactive))
            }
        }

    }

    private fun observeLiveData(view: View) {
        viewModel.actors.observe(viewLifecycleOwner, Observer {
            castAdapter = CastAdapter(it)

            view.findViewById<RecyclerView>(R.id.movie_details_cast_recycler_view).layoutManager = GridLayoutManager(context, 4)
            view.findViewById<RecyclerView>(R.id.movie_details_cast_recycler_view).adapter = castAdapter

        })

        viewModel.detail.observe(viewLifecycleOwner, Observer {
            dataBinding.movieDetail = it

            context?.let {context ->
                movieLink = it.homepage
            }

        })

        viewModel.movieDetailLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                view.findViewById<ConstraintLayout>(R.id.movie_details_movie_detail_container).visibility = View.GONE
                view.findViewById<ProgressBar>(R.id.movie_details_loading_progress_bar).visibility = View.VISIBLE
            } else {
                view.findViewById<ConstraintLayout>(R.id.movie_details_movie_detail_container).visibility = View.VISIBLE
                view.findViewById<ProgressBar>(R.id.movie_details_loading_progress_bar).visibility = View.GONE
            }
        })

        viewModel.actorsLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                view.findViewById<RecyclerView>(R.id.movie_details_cast_recycler_view).visibility = View.GONE
                view.findViewById<AppCompatTextView>(R.id.movie_details_detail_text_cast).visibility = View.GONE
            } else {
                view.findViewById<RecyclerView>(R.id.movie_details_cast_recycler_view).visibility = View.VISIBLE
                view.findViewById<AppCompatTextView>(R.id.movie_details_detail_text_cast).visibility = View.VISIBLE
            }
        })

        viewModel.videos.observe(viewLifecycleOwner, Observer {
            videosAdapter = VideosAdapter(it.results)

            view.findViewById<RecyclerView>(R.id.movie_details_actor_videos_recycler_view).layoutManager = GridLayoutManager(context, 3)
            view.findViewById<RecyclerView>(R.id.movie_details_actor_videos_recycler_view).adapter = videosAdapter

            if (it.results.isNotEmpty()){
                view.findViewById<AppCompatTextView>(R.id.movie_details_actor_detail_text_videos).visibility = View.VISIBLE
            }else{
                view.findViewById<AppCompatTextView>(R.id.movie_details_actor_detail_text_videos).visibility = View.GONE
            }

        })

        viewModel.isFavMovie.observe(viewLifecycleOwner, Observer {
            isFavMovie = it
            if (it) {
                //fav movie true
                view.findViewById<ImageButton>(R.id.movie_details_movie_fav_button).setImageDrawable(resources.getDrawable(R.drawable.fav_movie_icon_active))
            } else {
                view.findViewById<ImageButton>(R.id.movie_details_movie_fav_button).setImageDrawable(resources.getDrawable(R.drawable.fav_movie_icon_deactive))
            }
        })
    }
}