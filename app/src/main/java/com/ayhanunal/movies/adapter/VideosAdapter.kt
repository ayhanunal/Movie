package com.ayhanunal.movies.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.adapter.listener.VideoClickListener
import com.ayhanunal.movies.databinding.RowVideosBinding
import com.ayhanunal.movies.model.ResultX

class VideosAdapter(private val list: List<ResultX>): RecyclerView.Adapter<VideosAdapter.ViewHolder>(),
    VideoClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<RowVideosBinding>(inflater, R.layout.row_videos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.listener = this
        holder.view.video = list[position]
    }

    private fun openYoutube(view: View, youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            view.context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            view.context.startActivity(intentBrowser)
        }
    }

    override fun onVideoClicked(v: View) {
        val videoKey = v.findViewById<TextView>(R.id.row_videos_key_text).text.toString()
        openYoutube(v, videoKey)
    }

    class ViewHolder(var view: RowVideosBinding): RecyclerView.ViewHolder(view.root)
}