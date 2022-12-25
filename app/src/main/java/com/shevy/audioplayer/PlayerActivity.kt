package com.shevy.audioplayer

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevy.audioplayer.databinding.ActivityPlayerBinding
import com.shevy.audioplayer.model.Music

class PlayerActivity : AppCompatActivity() {

    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var mediaPlayer: MediaPlayer? = null
        /*
        var isPlaying:Boolean = false
        var musicService: MusicService? = null
        var repeat: Boolean = false
        var min15: Boolean = false
        var min30: Boolean = false
        var min60: Boolean = false
        var nowPlayingId: String = ""
        var isFavourite: Boolean = false
        var fIndex: Int = -1
        lateinit var loudnessEnhancer: LoudnessEnhancer*/
    }

    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                if (mediaPlayer == null) mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

            }
        }
    }
}