package com.ayhanunal.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.listener.MyItemClickListener
import com.ayhanunal.movies.databinding.RowFeedBinding
import com.ayhanunal.movies.model.Result
import com.ayhanunal.movies.view.FavMoviesFragmentDirections
import com.ayhanunal.movies.view.FeedFragmentDirections

class FeedAdapter(private val list: ArrayList<Result>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>(),
    MyItemClickListener {

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

    }

    fun updateList(updatedList: List<Result>) {
        list.clear()
        list.addAll(updatedList)
        notifyDataSetChanged()
    }

    // TODO: bu silinecek, test olarak dursun
    fun addPaginationList(updatedList: List<Result>) {
        list.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun onMyItemClicked(v: View) {
        val movieID = v.findViewById<TextView>(R.id.row_feed_movie_id).text.toString().toInt()
        if (Navigation.findNavController(v).currentDestination?.id == R.id.feedFragment){
            val action = FeedFragmentDirections.actionFeedFragmentToMovieDetailsFragment(movieID)
            Navigation.findNavController(v).navigate(action)
        }else{
            //current backstack is feed fragment
            //fav movies fragment to movies details fragment
            val action = FavMoviesFragmentDirections.actionFavMoviesFragmentToMovieDetailsFragment(movieID)
            Navigation.findNavController(v).navigate(action)
        }
    }

    class FeedViewHolder(var view: RowFeedBinding): RecyclerView.ViewHolder(view.root) { }
}