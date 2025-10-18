package com.pjsoft.musicapp.services

import com.pjsoft.musicapp.models.Album
import retrofit2.http.GET

interface AlbumService {

    @GET("albums")
    suspend fun getAllAlbums() : List<Album>

    @GET("albums/{id}")
    suspend fun getAlbumById(@retrofit2.http.Path("id") id: String) : Album

}