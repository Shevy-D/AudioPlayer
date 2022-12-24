package com.shevy.audioplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shevy.audioplayer.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AudioPlayer)

        super.onCreate(savedInstanceState)
        requestRuntimePermission()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initializeLayout()

        binding.shuffleBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, PlayerActivity::class.java))
        }

        binding.favouriteBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }

        binding.playlistBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, PlaylistActivity::class.java))
        }

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navFeedback -> Toast.makeText(baseContext, "Feedback", Toast.LENGTH_SHORT).show()
                R.id.navSettings -> Toast.makeText(baseContext, "Setting", Toast.LENGTH_SHORT).show()
                R.id.navAbout -> Toast.makeText(baseContext, "About", Toast.LENGTH_SHORT).show()
                R.id.navExit -> exitProcess(1)
            }
            true
        }

        /*       val mediaPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.music)

               val seekBar = binding.seekBar
               seekBar.progress = 0
               seekBar.max = mediaPlayer.duration

               binding.playBtn.setOnClickListener {
                   if (!mediaPlayer.isPlaying) {
                       mediaPlayer.start()
                       binding.playBtn.setImageResource(R.drawable.ic_pause)
                   } else {
                       mediaPlayer.pause()
                       binding.playBtn.setImageResource(R.drawable.ic_play)
                   }
               }

               seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                   override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                       if (changed) {
                           mediaPlayer.seekTo(pos)
                       }
                   }

                   override fun onStartTrackingTouch(p0: SeekBar?) {
                   }

                   override fun onStopTrackingTouch(p0: SeekBar?) {
                   }
               })

               runnable = Runnable {
                   seekBar.progress = mediaPlayer.currentPosition
                   handler.postDelayed(runnable, 1000)
               }
               handler.postDelayed(runnable, 1000)

               mediaPlayer.setOnCompletionListener {
                   binding.playBtn.setImageResource(R.drawable.ic_play)
                   seekBar.progress = 0
               }*/
    }

    private fun requestRuntimePermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 13) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                //initializeLayout()
            } else
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    13
                )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    private fun initializeLayout(){
        val musicList = ArrayList<String>()
        musicList.add("1 song")
        musicList.add("2 song")
        musicList.add("3 song")
        musicList.add("4 song")
        musicList.add("5 song")
        binding.musicRV.setHasFixedSize(true)
        binding.musicRV.setItemViewCacheSize(13)
        binding.musicRV.layoutManager = LinearLayoutManager(this@MainActivity)
        musicAdapter = MusicAdapter(this@MainActivity, musicList)
        binding.musicRV.adapter = musicAdapter
        binding.totalSongs.text = "Total Songs: ${musicAdapter.itemCount}"
/*        search = false
        val sortEditor = getSharedPreferences("SORTING", MODE_PRIVATE)
        sortOrder = sortEditor.getInt("sortOrder", 0)
        MusicListMA = getAllAudio()

        binding.totalSongs.text  = "Total Songs : "+musicAdapter.itemCount

        //for refreshing layout on swipe from top
        binding.refreshLayout.setOnRefreshListener {
            MusicListMA = getAllAudio()
            musicAdapter.updateMusicList(MusicListMA)

            binding.refreshLayout.isRefreshing = false
        }*/
    }
}