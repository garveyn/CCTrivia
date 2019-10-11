package com.ngarvey.cctrivia

import com.google.gson.annotations.SerializedName


data class Question(
    @SerializedName("question")
    val question: String? = null,

    @SerializedName("answer")
    val answers: Array<String>? = null,

    @SerializedName("correctAnswerIndex")
    val correctAnswer: Int? = null,

    @SerializedName("type")
    val answerType: Int? = null
)
