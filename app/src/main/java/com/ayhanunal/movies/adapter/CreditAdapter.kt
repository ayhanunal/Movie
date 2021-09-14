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
import com.ayhanunal.movies.databinding.RowCreditsBinding
import com.ayhanunal.movies.model.CastX
import com.ayhanunal.movies.view.PersonDetailsFragmentDirections

class CreditAdapter(private val list: List<CastX>) : RecyclerView.Adapter<CreditAdapter.ViewHolder>(),
    MyItemClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RowCreditsBinding>(inflater, R.layout.row_credits, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.listener = this
        holder.view.credit = list[position]
    }

    override fun onMyItemClicked(v: View) {
        val movieID = v.findViewById<TextView>(R.id.row_credits_credit_id).text.toString().toInt()
        val action = PersonDetailsFragmentDirections.actionPersonDetailsFragmentToMovieDetailsFragment(movieID)
        Navigation.findNavController(v).navigate(action)
    }

    class ViewHolder(var view: RowCreditsBinding): RecyclerView.ViewHolder(view.root)
}