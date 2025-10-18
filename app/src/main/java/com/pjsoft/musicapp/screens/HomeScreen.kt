package com.pjsoft.musicapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pjsoft.musicapp.models.Album
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pjsoft.musicapp.components.RecentlyPlayedCard
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.pjsoft.musicapp.components.AlbumCard
import com.pjsoft.musicapp.services.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.pjsoft.musicapp.screens.AlbumDetailScreenRoute

@Composable
fun HomeScreen(navController: NavController){
    val BASE_URL = "https://music.juanfrausto.com/api/"
     var albums by remember { mutableStateOf(listOf<Album>()) }
     var loading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val service = retrofit.create(AlbumService::class.java)
            val result = withContext(Dispatchers.IO) {
                service.getAllAlbums()
            }
            Log.d("HomeScreen", "Respuesta de la API: $result")
            albums = result
            loading = false
        } catch (e: Exception) {
            Log.e("HomeScreen", "Error fetching products", e)
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
    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 15.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .height(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(
                        color = Color(0xFF9B6EF3),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Good Morning!",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Héctor Adrián",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Albums",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
                Text("See more",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 8.dp, end = 16.dp),
                    fontSize = 16.sp,
                    color = Color(0xFF9B6EF3)
                )
            }
            LazyRow {
                items(albums) { album ->
                    AlbumCard(
                        album = album,
                        onClick = { navController.navigate(AlbumDetailScreenRoute(album.id)) }
                    )

                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Recently Played",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black)
                Text("See more",
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp, bottom = 8.dp, end = 16.dp),
                    fontSize = 16.sp,
                    color = Color(0xFF9B6EF3)
                )
            }

            LazyColumn {
                items(albums) { album ->
                    RecentlyPlayedCard(
                        album = album,
                        onClick = { navController.navigate(AlbumDetailScreenRoute(album.id)) }
                    )

                }
            }

        }


}

//@Preview
//@Composable
//fun HomeScreenPreview(){
//    val navController = rememberNavController()
//    HomeScreen(navController)
//}