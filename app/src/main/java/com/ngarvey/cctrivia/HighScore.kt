package com.ngarvey.cctrivia

import com.google.gson.annotations.SerializedName

data class HighScore(
    @SerializedName("rank")
    val rank: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("score")
    val score: Int? = null
)