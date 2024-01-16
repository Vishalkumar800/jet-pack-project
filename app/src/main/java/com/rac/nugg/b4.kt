package com.rac.nugg

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView


@Composable
fun Bscreen(sharedViewModel: SharedViewModel) {

    val videoLink by sharedViewModel.videoLink.collectAsState()

    Column {
        // Replace "your_firebase_storage_url" with the actual URL of your Firebase Storage video
        val videoUrl = videoLink.orEmpty()
        val context = LocalContext.current
        val player = SimpleExoPlayer.Builder(context).build()
        val playerView = PlayerView(context)
        val mediaItem = MediaItem.fromUri(videoUrl)
        val playWhenReady by rememberSaveable {
            mutableStateOf(true)
        }

        player.setMediaItem(mediaItem)
        playerView.player = player

        LaunchedEffect(player) {
            player.prepare()
            player.playWhenReady = playWhenReady
        }

        AndroidView(factory = {
            playerView
        })
    }
}
