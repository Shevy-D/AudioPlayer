package com.shevy.audioplayer.presentation.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.shevy.audioplayer.databinding.ActivityFavoriteBinding
import com.shevy.audioplayer.models.Music

class FavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var adapter: FavoriteAdapter

    companion object {
        var favoriteSongs: ArrayList<Music> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtnFA.setOnClickListener { finish() }

        binding.favouriteRV.setHasFixedSize(true)
        binding.favouriteRV.setItemViewCacheSize(13)
        binding.favouriteRV.layoutManager = GridLayoutManager(this@FavoriteActivity, 4)
        adapter = FavoriteAdapter(this@FavoriteActivity, favoriteSongs)
        binding.favouriteRV.adapter = adapter
    }
}