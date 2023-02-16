package com.shevy.audioplayer.presentation.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.shevy.audioplayer.databinding.ActivityFavoriteBinding
import com.shevy.audioplayer.models.Music
import com.shevy.audioplayer.models.checkPlaylist
import com.shevy.audioplayer.presentation.MainActivity
import com.shevy.audioplayer.presentation.PlayerActivity

class FavoriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityFavoriteBinding
    lateinit var adapter: FavoriteAdapter

    companion object {
        var favoriteSongs: ArrayList<Music> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteSongs = checkPlaylist(favoriteSongs)

        binding.backBtnFA.setOnClickListener { finish() }

        binding.favouriteRV.setHasFixedSize(true)
        binding.favouriteRV.setItemViewCacheSize(13)
        binding.favouriteRV.layoutManager = GridLayoutManager(this@FavoriteActivity, 4)
        adapter = FavoriteAdapter(this@FavoriteActivity, favoriteSongs)
        binding.favouriteRV.adapter = adapter

        if (favoriteSongs.size > 1) {
            binding.shuffleBtnFA.setOnClickListener {
                val intent = Intent(this@FavoriteActivity, PlayerActivity::class.java)
                intent.putExtra("index", 0)
                intent.putExtra("class", "FavoriteShuffle")
                startActivity(intent)
            }
        }
    }
}