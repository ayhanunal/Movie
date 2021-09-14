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
import com.ayhanunal.movies.databinding.RowCastBinding
import com.ayhanunal.movies.model.Cast
import com.ayhanunal.movies.view.MovieDetailsFragmentDirections

class CastAdapter(private val list: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>(), MyItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RowCastBinding>(inflater, R.layout.row_cast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.listener = this
        holder.view.cast = list[position]
    }

    override fun onMyItemClicked(v: View) {
        val actorID = v.findViewById<TextView>(R.id.row_cost_actor_id).text.toString().toInt()
        val action = MovieDetailsFragmentDirections.actionMovieDetailsFragmentToPersonDetailsFragment(actorID)
        Navigation.findNavController(v).navigate(action)
    }

    class ViewHolder(var view: RowCastBinding): RecyclerView.ViewHolder(view.root)
}