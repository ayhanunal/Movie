package com.ayhanunal.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.model.CastX
import com.ayhanunal.movies.util.downloadFromUrl
import com.ayhanunal.movies.util.placeholderProgressBar
import com.ayhanunal.movies.view.PersonDetailsFragmentDirections
import kotlinx.android.synthetic.main.row_credits.view.*

class CreditAdapter(private val list: List<CastX>) : RecyclerView.Adapter<CreditAdapter.ViewHolder>() {

    private val IMAGE_PATH = "https://image.tmdb.org/t/p/w185"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_credits, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.row_credits_credit_image.downloadFromUrl(IMAGE_PATH + list[position].backdrop_path, placeholderProgressBar(holder.itemView.context))
        holder.itemView.row_credits_credit_name.text = list[position].title

        holder.itemView.setOnClickListener {
            val action = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(list[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}