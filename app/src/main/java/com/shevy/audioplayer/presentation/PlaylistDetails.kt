package com.shevy.audioplayer.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder
import com.shevy.audioplayer.MusicAdapter
import com.shevy.audioplayer.R
import com.shevy.audioplayer.databinding.ActivityPlaylistDetailsBinding
import com.shevy.audioplayer.models.checkPlaylist
import com.shevy.audioplayer.presentation.playlist.PlaylistActivity

class PlaylistDetails : AppCompatActivity() {

    lateinit var binding: ActivityPlaylistDetailsBinding
    lateinit var adapterRV: MusicAdapter

    companion object {
        var currentPlaylistPos: Int = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(MainActivity.currentThemeNav[MainActivity.themeIndex])
        binding = ActivityPlaylistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentPlaylistPos = intent.extras?.get("index") as Int
        try {
            PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist =
                checkPlaylist(playlist = PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist)
        } catch (e: Exception) {
        }
        binding.playlistDetailsRV.setItemViewCacheSize(10)
        binding.playlistDetailsRV.setHasFixedSize(true)
        binding.playlistDetailsRV.layoutManager = LinearLayoutManager(this@PlaylistDetails)
        adapterRV = MusicAdapter(
            this,
            PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist,
            playlistDetails = true
        )
        binding.playlistDetailsRV.adapter = adapterRV
        binding.backBtnPD.setOnClickListener { finish() }
        binding.shuffleBtnPD.setOnClickListener {
            val intent = Intent(this@PlaylistDetails, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "PlaylistDetailShuffle")
            startActivity(intent)
        }
        binding.addBtnPD.setOnClickListener {
            startActivity(Intent(this, SelectionActivity::class.java))
        }
        binding.removeAllPD.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Remove")
                .setMessage("Do you want to remove all songs from playlist?")
                .setPositiveButton("Yes") { dialog, _ ->
                    PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist.clear()
                    adapterRV.refreshPlaylist()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            val customDialog = builder.create()
            customDialog.show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding.playlistNamePD.text = PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].name
        binding.moreInfoPD.text = "Total ${adapterRV.itemCount} Songs.\n\n" +
                "Created On:\n${PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].createdOn}\n\n" +
                "  -- ${PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].createdBy}"
        if (adapterRV.itemCount > 0) {
            Glide.with(this)
                .load(PlaylistActivity.musicPlaylist.ref[currentPlaylistPos].playlist[0].artUri)
                .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
                .into(binding.playlistImgPD)
            binding.shuffleBtnPD.visibility = View.VISIBLE
        }
        adapterRV.notifyDataSetChanged()

        val editor = getSharedPreferences("FAVORITES", MODE_PRIVATE).edit()
        val jsonStringPlaylist = GsonBuilder().create().toJson(PlaylistActivity.musicPlaylist)
        editor.putString("MusicPlaylist", jsonStringPlaylist)
        editor.apply()
    }
}