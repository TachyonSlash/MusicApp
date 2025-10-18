package com.pjsoft.musicapp.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.pjsoft.musicapp.models.Album
import com.pjsoft.musicapp.services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun DetailScreen(albumId: String){
    var album by remember { mutableStateOf<Album?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(albumId) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://music.juanfrausto.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AlbumService::class.java)
        val result = withContext(Dispatchers.IO) {
            service.getAlbumById(albumId)
        }
        album = result
        loading = false
    }

    if (loading) {
        CircularProgressIndicator()
    } else {
        Text("Hola")
    }
}