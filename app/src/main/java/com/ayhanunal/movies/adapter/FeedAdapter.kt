package com.ayhanunal.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.util.downloadFromUrl
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.row_feed.view.*

class FeedAdapter(private val list: ArrayList<Result>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w185"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.itemView.row_feed_movie_name.text = list[position].title
        holder.itemView.row_feed_movie_language.text = "Language: ${list[position].original_language}"
        holder.itemView.row_feed_movie_date.text = "Release Date: ${list[position].release_date}"
        holder.itemView.row_feed_movie_popularity.text = "Popularity: ${list[position].popularity}"
        holder.itemView.row_feed_movie_adult.text = "Adult: " + if (list[position].adult) "Yes" else "No"
        holder.itemView.row_feed_movie_image.downloadFromUrl(IMAGE_PATH + list[position].poster_path, placeholderProgressBar(holder.itemView.context))

        holder.itemView.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToMovieDetailsFragment(list[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateList(updatedList: List<Result>) {
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }

    fun addPaginationList(updatedList: List<Result>) {
        list.addAll(updatedList)
        notifyDataSetChanged()
    }


    class FeedViewHolder(view: View): RecyclerView.ViewHolder(view) { }
}