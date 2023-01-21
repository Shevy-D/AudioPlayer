package com.shevy.audioplayer.presentation.playlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shevy.audioplayer.databinding.PlaylistViewBinding
import com.shevy.audioplayer.models.Playlist

class PlaylistViewAdapter(private val context: Context, private var playlistList: ArrayList<Playlist>) : RecyclerView.Adapter<PlaylistViewAdapter.MyHolder>() {

    class MyHolder(binding: PlaylistViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.playlistImg
        val name = binding.playlistName
        val root = binding.root
        val delete = binding.playlistDeleteBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(PlaylistViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = playlistList[position].name
        holder.name.isSelected = true

    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshPlaylist(){
        playlistList = ArrayList()
        playlistList.addAll(PlaylistActivity.musicPlaylist.ref)
        notifyDataSetChanged()
    }
}