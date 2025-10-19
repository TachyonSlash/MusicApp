package com.pjsoft.musicapp.models

import com.google.gson.annotations.SerializedName

data class Album(
    val title : String,
    val artist : String,
    val description : String,
    val image : String,
    @SerializedName("id", alternate = ["_id"])
    val id : String
)


