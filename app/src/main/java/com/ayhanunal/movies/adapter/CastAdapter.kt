package com.ayhanunal.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.model.Cast
import com.ayhanunal.movies.util.downloadImage
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.view.MovieDetailsFragmentDirections
import kotlinx.android.synthetic.main.row_cast.view.*

class CastAdapter(private val list: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w185"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.row_cast_actor_image.downloadImage(IMAGE_PATH + list[position].profile_path, placeholderProgressBar(holder.itemView.context))
        holder.itemView.row_cast_actor_name.text = list[position].name

        holder.itemView.setOnClickListener {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(list[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}