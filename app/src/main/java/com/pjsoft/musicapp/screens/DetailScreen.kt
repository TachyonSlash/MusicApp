package com.pjsoft.musicapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.pjsoft.musicapp.components.RecentlyPlayedCard
import com.pjsoft.musicapp.models.Album
import com.pjsoft.musicapp.services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun DetailScreen(albumId: String, navController: NavController) {
    var album by remember { mutableStateOf<Album?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(albumId) {
        Log.d("DetailScreen", "Solicitando álbum con id: $albumId")
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://music.juanfrausto.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getAlbumById(albumId)
            }
            Log.e("DetailScreen", "Álbum recibido: $result")
            album = result
            loading = false
        } catch (e: Exception) {
            Log.e("DetailScreen", "Error al obtener el álbum", e)
            loading = false
        }
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (album == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No se pudo cargar el álbum. Intenta más tarde.", color = Color.Red)
        }
    } else {
        val currentAlbum = album!!
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(32.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(currentAlbum.image),
                    contentDescription = currentAlbum.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(32.dp)),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xAA9B6EF3), Color.Transparent),
                                startY = 0f,
                                endY = 400f
                            ),
                            shape = RoundedCornerShape(32.dp)
                        )
                )
                IconButton(
                    onClick = { navController.navigate(HomeScreenRoute) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 22.dp, top = 16.dp)
                        .size(32.dp)
                        .background(Color(0x80000000), shape = RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 22.dp, top = 16.dp)
                        .size(32.dp)
                        .background(Color(0x80000000), shape = RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    Text(
                        text = currentAlbum.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentAlbum.artist,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        IconButton(onClick = { },
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color(0xFF9B6EF3), shape = RoundedCornerShape(50))
                                ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(28.dp))
                        IconButton(onClick = { },
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, shape = RoundedCornerShape(50))
                            ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Shuffle",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "About this album",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = currentAlbum.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFEDE7F6)
                ) {
                    Text(
                        text = "Artist: ${currentAlbum.artist}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                items(10) { i ->
                    RecentlyPlayedCard(
                        album = currentAlbum.copy(
                            title = "${currentAlbum.title} • Track ${i + 1}"
                        ),
                        onClick = { }
                    )
                }
            }


            Box(
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(Color(0xFF9B6EF3), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(currentAlbum.image),
                        contentDescription = currentAlbum.title,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = currentAlbum.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = currentAlbum.artist,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
