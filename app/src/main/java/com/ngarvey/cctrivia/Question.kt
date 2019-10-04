package com.ngarvey.cctrivia

import org.json.JSONObject

class Question(json: String) : JSONObject(json) {
    val question: String? = this.optString("question")
    val answers = this.optJSONArray("answers")
            ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
            ?.map { it.toString() }

}