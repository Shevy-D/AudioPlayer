package com.shevy.audioplayer.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevy.audioplayer.databinding.ActivityPlaylistDetailsBinding

class PlaylistDetails : AppCompatActivity() {

    lateinit var binding: ActivityPlaylistDetailsBinding

    companion object {
        var currentPlaylistPos: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaylistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPlaylistPos = intent.extras?.get("position") as Int
    }
}