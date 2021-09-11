package com.ayhanunal.movies.adapter

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayhanunal.movies.R
import com.ayhanunal.movies.model.ResultX
import com.ayhanunal.movies.util.downloadImage
import com.ayhanunal.movies.util.placeholderProgressBar
import kotlinx.android.synthetic.main.row_videos.view.*

class VideosAdapter(private val list: List<ResultX>): RecyclerView.Adapter<VideosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_videos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.row_videos_name_text.text = list[position].name
        holder.itemView.row_videos_image.downloadImage("https://img.youtube.com/vi/"+list[position].key+"/0.jpg", placeholderProgressBar(holder.itemView.context))

        holder.itemView.setOnClickListener {
            openYoutubeLink(holder.itemView, list[position].key)
        }
    }

    private fun openYoutubeLink(view: View, youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            view.context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            view.context.startActivity(intentBrowser)
        }
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}