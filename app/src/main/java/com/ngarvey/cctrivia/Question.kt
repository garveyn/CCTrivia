package com.ngarvey.cctrivia

import org.json.JSONObject


// Code inspired by https://stackoverflow.com/questions/41928803/how-to-parse-json-in-kotlin
//      by username: frouo
class Question(json: String) : JSONObject(json) {
    val question: String? = this.optString("question")
    val answers = this.optJSONArray("answers")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
            ?.map { it.toString() }
    val correctAnswer: Int? = this.optInt("correct")
    val answerType: Int? = this.optInt("type")
}