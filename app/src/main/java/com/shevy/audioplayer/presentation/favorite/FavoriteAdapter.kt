package com.shevy.audioplayer.presentation.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shevy.audioplayer.R
import com.shevy.audioplayer.databinding.FavoriteViewBinding
import com.shevy.audioplayer.models.Music
import com.shevy.audioplayer.presentation.PlayerActivity

class FavoriteAdapter(
    private val context: Context,
    private var musicList: ArrayList<Music>
) : RecyclerView.Adapter<FavoriteAdapter.MyHolder>() {

    class MyHolder(binding: FavoriteViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.songImgFV
        val name = binding.songNameFV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(FavoriteViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = musicList[position].title
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    private fun sendIntent(ref: String, pos: Int) {
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra("index", pos)
        intent.putExtra("class", ref)
        ContextCompat.startActivity(context, intent, null)
    }
}