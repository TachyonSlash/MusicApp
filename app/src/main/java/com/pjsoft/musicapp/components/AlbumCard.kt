package com.pjsoft.musicapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.pjsoft.musicapp.models.Album

@Composable
fun AlbumCard(
    album: Album,
    onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(32.dp))
            .clickable{ onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(album.image),
            contentDescription = album.title,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(32.dp)),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color(0x80000000), RoundedCornerShape(32.dp))
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = album.title,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = album.artist,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun AlbumCardPreview(){
    AlbumCard(
        album = Album(
            id = "1",
            title = "Xenoblade Chronicles 3",
            artist = "Noah Vandham",
            description = "Off-seering theme",
            image = "https://static.wikia.nocookie.net/xenoblade/images/0/09/NSO_N_Character_Icon.png/revision/latest/scale-to-width/360?cb=20250130033435"
        ),
        onClick = {}
    )
}