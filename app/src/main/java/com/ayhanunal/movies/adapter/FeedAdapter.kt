package com.ayhanunal.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.databinding.RowFeedBinding
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.util.downloadImage
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.row_feed.view.*

class FeedAdapter(private val list: ArrayList<Result>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>(), MovieClickListener {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w185"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RowFeedBinding>(inflater, R.layout.row_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        holder.view.listener = this
        holder.view.movie = list[position]
        holder.itemView.row_feed_movie_image.downloadImage(IMAGE_PATH + list[position].poster_path, placeholderProgressBar(holder.itemView.context))
        holder.itemView.row_feed_movie_adult.text = "Adult: " + if (list[position].adult) "Yes" else "No"

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

    override fun onMovieClicked(v: View) {
        val movieID = v.findViewById<TextView>(R.id.row_feed_movie_id).text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToMovieDetailsFragment(movieID)
        Navigation.findNavController(v).navigate(action)
    }

    class FeedViewHolder(var view: RowFeedBinding): RecyclerView.ViewHolder(view.root) { }
}